package com.madsq.weather.presentation.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.madsq.weather.databinding.FragmentHomeBinding
import com.madsq.weather.presentation.home.adpater.HomeAdapter
import com.madsq.weather.presentation.home.data.model.HomeAlertItem
import kotlinx.coroutines.launch


/**
 * Created by mihai.adascalitei on 24.10.2022.
 */

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding

    private val viewModel: HomeViewModel by viewModels()
    private val homeAdapter: HomeAdapter by lazy { HomeAdapter(_onHomeItemClick) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentHomeBinding.inflate(inflater, container, false).apply {
        binding = this
    }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvHome.adapter = homeAdapter
        binding.rvHome.addOnScrollListener(_scrollListener)
        binding.btnRetry.setOnClickListener {
            binding.llError.isVisible = false
            viewModel.getAlerts()
        }

        lifecycleScope.launch {

            viewModel.alertsUiState.collect {
                when (it) {
                    is HomeViewModel.AlertsUiState.Success -> {
                        binding.llError.isVisible = it.alerts.isEmpty()
                        homeAdapter.updateItems(it.alerts)
                    }
                    is HomeViewModel.AlertsUiState.Loading -> {
                        binding.loading.isVisible = it.isLoading
                    }
                }
            }
        }
    }

    private val _scrollListener = object : RecyclerView.OnScrollListener() {
        fun RecyclerView.getRangeUpdates() {
            (layoutManager as? LinearLayoutManager)?.let { layManager -> //request for only the visible items
                val firstPosition = layManager.findFirstVisibleItemPosition()
                val lastPosition = layManager.findLastVisibleItemPosition()
                viewModel.getPicsForRange(firstPosition, lastPosition)
            }
        }

        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            if (newState != RecyclerView.SCROLL_STATE_IDLE) {
                return
            }
            recyclerView.getRangeUpdates()
        }

        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            if (dx == 0 && dy == 0) {//need to check the first time updates
                recyclerView.getRangeUpdates()
            }
        }
    }

    private val _onHomeItemClick: (HomeAlertItem) -> Unit = {
        findNavController().navigate(HomeFragmentDirections.actionHomeToDetails(it))
    }
}