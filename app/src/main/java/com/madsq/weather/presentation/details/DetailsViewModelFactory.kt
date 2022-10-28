package com.madsq.weather.presentation.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.madsq.weather.presentation.home.data.model.HomeAlertItem


/**
 * Created by mihai.adascalitei on 28.10.2022.
 */

//factory to help out pass param to VM
class DetailsViewModelFactory(private val item: HomeAlertItem) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        modelClass
            .getConstructor(HomeAlertItem::class.java)
            .newInstance(item)
}