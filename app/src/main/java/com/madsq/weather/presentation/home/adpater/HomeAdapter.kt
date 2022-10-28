package com.madsq.weather.presentation.home.adpater

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.madsq.weather.databinding.ItemHomeBinding
import com.madsq.weather.presentation.home.data.model.HomeAlertItem


/**
 * Created by mihai.adascalitei on 25.10.2022.
 */

class HomeAdapter(
    private val onHomeItemClick: (HomeAlertItem) -> Unit
) : RecyclerView.Adapter<HomeViewHolder>() {
    private var items: List<HomeAlertItem> = listOf()

    fun updateItems(newItems: List<HomeAlertItem>) {
        val diff = DiffUtil.calculateDiff(HomeDiffCallback(items, newItems))
        items = items.toMutableList().apply {
            clear()
            addAll(newItems)
        }
        diff.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = HomeViewHolder(
        ItemHomeBinding.inflate(LayoutInflater.from(parent.context), parent, false),
        onHomeItemClick
    )

    override fun onBindViewHolder(
        holder: HomeViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        holder.bind(items[position], payloads)
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        holder.bind(items[position], mutableListOf())
    }

    override fun getItemCount() = items.size
}