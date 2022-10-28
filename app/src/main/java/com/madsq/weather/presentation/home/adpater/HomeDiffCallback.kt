package com.madsq.weather.presentation.home.adpater

import androidx.recyclerview.widget.DiffUtil
import com.madsq.weather.presentation.home.data.model.HomeAlertItem
import com.madsq.weather.presentation.home.data.model.HomePayloads


/**
 * Created by mihai.adascalitei on 25.10.2022.
 */

class HomeDiffCallback(
    private val oldList: List<HomeAlertItem>,
    private val newList: List<HomeAlertItem>
) : DiffUtil.Callback() {
    override fun getOldListSize() = oldList.size
    override fun getNewListSize() = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) =
        oldList[oldItemPosition] == newList[newItemPosition]

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int) = run {
        val oldItem = oldList[oldItemPosition]
        val newItem = newList[newItemPosition]

        oldItem.id == newItem.id && oldItem.imageUrl == newItem.imageUrl
    }

    override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? = run {
        val oldItem = oldList[oldItemPosition]
        val newItem = newList[newItemPosition]
        if (oldItem.imageUrl != newItem.imageUrl) {
            HomePayloads.UPDATE_URL
        } else null
    }
}