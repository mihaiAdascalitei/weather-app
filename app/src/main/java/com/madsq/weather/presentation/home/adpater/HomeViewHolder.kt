package com.madsq.weather.presentation.home.adpater

import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.madsq.weather.R
import com.madsq.weather.databinding.ItemHomeBinding
import com.madsq.weather.presentation.home.data.model.HomeAlertItem
import com.madsq.weather.presentation.home.data.model.HomePayloads


/**
 * Created by mihai.adascalitei on 25.10.2022.
 */

class HomeViewHolder(
    private val binding: ItemHomeBinding,
    private val onHomeItemClick: (HomeAlertItem) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: HomeAlertItem, payloads: MutableList<Any>) {
        if (payloads.isEmpty()) { //payloads to update only partial changes
            binding.tvName.text = item.areaDesc
            binding.tvEvent.text = item.event
            binding.tvSeverity.apply {
                text = item.severity
                setTextColor(ContextCompat.getColor(itemView.context, item.severityColor))
            }
            val sender = itemView.context.getString(R.string.by, item.senderName)
            binding.tvSender.text = sender

            val starts = itemView.context.getString(R.string.starts, item.sent)
            binding.tvDateStart.text = starts

            val ends = itemView.context.getString(R.string.ends, item.end)
            binding.tvDateEnd.text = ends

            loadImage(item)

        } else {
            when (payloads[0]) {
                HomePayloads.UPDATE_URL -> {
                    loadImage(item)
                }
            }
        }

        itemView.setOnClickListener {
            onHomeItemClick(item)
        }
    }

    private fun loadImage(item: HomeAlertItem) {
        item.imageUrl.takeIf { it.isNotEmpty() }?.let { url ->
            Glide.with(itemView.context).load(url).error(R.drawable.ic_weather)
                .into(binding.ivImage)
        } ?: binding.ivImage.setImageResource(R.drawable.solid_rectangle)
    }
}