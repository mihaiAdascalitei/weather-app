package com.madsq.weather.presentation.details

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.google.android.material.chip.Chip
import com.madsq.weather.R
import com.madsq.weather.databinding.AffectedZoneItemBinding
import com.madsq.weather.databinding.FragmentDetailsBinding
import com.madsq.weather.presentation.details.data.model.AffectedZoneItem
import com.madsq.weather.presentation.details.data.model.ExpandableItem
import kotlinx.coroutines.launch


/**
 * Created by mihai.adascalitei on 24.10.2022.
 */

class DetailsFragment : Fragment() {

    private lateinit var binding: FragmentDetailsBinding
    private val args: DetailsFragmentArgs by navArgs()

    private val viewModel: DetailsViewModel by viewModels(
        factoryProducer = { DetailsViewModelFactory(args.item) }
    )

    private var originalDraggableX = 0f
    private var originalDraggableY = 0f

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View =
        FragmentDetailsBinding.inflate(inflater, container, false).apply {
            binding = this
        }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindView(view.context)
        setupDraggableListener()

        lifecycleScope.launch {

            viewModel.zonesUiState.collect {
                if (it is DetailsViewModel.ZonesUiState.Success) {
                    bindAffectedZones(it.zones)
                }
            }
        }
    }

    private fun bindView(ctx: Context) {
        val item = args.item
        binding.toolbar.apply {
            title = item.areaDesc
            setNavigationIcon(R.drawable.ic_baseline_arrow_back_24)
            setNavigationOnClickListener {
                activity?.onBackPressed()
            }
        }

        Glide.with(ctx).load(item.imageUrl).error(R.drawable.ic_weather).into(binding.ivImage)
        Glide.with(ctx).load(item.imageUrl).error(R.drawable.ic_weather)
            .into(binding.ivImageDraggable)

        binding.tvDate.text = "${item.sent} - ${item.end}"
        binding.tvCertainty.text = item.certainty
        binding.tvUrgency.text = item.urgency
        binding.tvSender.text = context?.getString(R.string.by, item.senderName)
        binding.tvSeverity.apply {
            text = item.severity
            setTextColor(ContextCompat.getColor(context, item.severityColor))
        }

        val description = ExpandableItem(
            title = context?.getString(R.string.description).orEmpty(),
            data = item.description
        )
        binding.expandableDescription.bind(description)

        item.instruction.takeIf { it.isNotEmpty() }?.let {
            val instruction = ExpandableItem(
                title = context?.getString(R.string.instructions).orEmpty(),
                data = it
            )
            binding.expandableInstruction.bind(instruction)
            binding.expandableInstruction.isVisible = true
        }
    }

    private fun bindAffectedZones(zones: List<AffectedZoneItem>) {
        binding.llAffectedZones.isVisible = zones.isNotEmpty()
        binding.cgZones.apply {
            removeAllViews()
            zones.forEach { zone ->
                createChip(zone).also { zoneView ->
                    addView(zoneView)
                }
            }
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setupDraggableListener() {
        calculateOriginalViewPosition()

        val dragListener = View.OnTouchListener { view, motionEvent ->
            if (motionEvent.action == MotionEvent.ACTION_MOVE) {
                view.y = motionEvent.rawY - view.height / 2
                view.x = motionEvent.rawX - view.width / 2
            }
            true
        }
        binding.ivImage.setOnLongClickListener {
            binding.cvImageDraggable.isVisible = true
            binding.tvDragging.isVisible = true
            binding.cvImageDraggable.setOnTouchListener(dragListener)
            true
        }

        binding.ivDragCancel.setOnClickListener {
            binding.tvDragging.isVisible = false
            binding.cvImageDraggable.isVisible = false
            binding.cvImageDraggable.apply {
                x = originalDraggableX
                y = originalDraggableY
            }
        }
    }

    private fun calculateOriginalViewPosition() {
        binding.cvImageDraggable.viewTreeObserver.addOnGlobalLayoutListener {
            if (originalDraggableX == 0f) {
                originalDraggableX = binding.cvImageDraggable.x
            }
            if (originalDraggableY == 0f) {
                originalDraggableY = binding.cvImageDraggable.y
            }
        }
    }

    private fun createChip(item: AffectedZoneItem) =
        AffectedZoneItemBinding.inflate(layoutInflater).apply {
            chip.text = item.name
        }.root
}