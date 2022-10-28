package com.madsq.weather.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.madsq.weather.injection.provideHomeRepository
import com.madsq.weather.presentation.home.data.model.HomeAlertItem
import com.madsq.weather.presentation.home.utils.alertsQueryParams
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch


/**
 * Created by mihai.adascalitei on 24.10.2022.
 */

class HomeViewModel : ViewModel() {

    private val homeRepo by lazy { provideHomeRepository() }

    private val _alertsUiState = MutableSharedFlow<AlertsUiState>()
    val alertsUiState: SharedFlow<AlertsUiState> = _alertsUiState.asSharedFlow()

    private val picturesBatch =
        mutableMapOf<Int, Boolean>() //item index and if it was already executed
    private var items = mutableListOf<HomeAlertItem>()
    private var isFetchingPics = false

    init {
        getAlerts()
    }

    fun getAlerts() {
        viewModelScope.launch {
            showLoading()
            homeRepo.getAlerts(alertsQueryParams()).let { response ->
                items = response.toMutableList()
                _alertsUiState.emit(AlertsUiState.Success(response))
            }
            showLoading(false)
        }
    }

    fun getPicsForRange(firstPosition: Int, lastPosition: Int) {
        if (isFetchingPics.not()) {
            (firstPosition..lastPosition).forEach {
                if (picturesBatch.contains(it).not()) {
                    picturesBatch[it] = false
                }
            }
            getPics()
        }

    }

    private fun getPics() {
        isFetchingPics = true
        viewModelScope.launch {
            val unfetchedPictures = picturesBatch.count { it.value.not() }
            homeRepo.getPics(unfetchedPictures).also { pictures ->
                isFetchingPics = false
                var index = 0

                picturesBatch.filter { it.value.not() }.forEach { (key, value) ->
                    items[key] = items[key].copy(imageUrl = pictures.getOrNull(index++).orEmpty())
                    picturesBatch[key] = true
                }

                _alertsUiState.emit(AlertsUiState.Success(items))
            }
        }
    }

    private suspend fun showLoading(isLoading: Boolean = true) {
        val loadingUiState = AlertsUiState.Loading(isLoading)
        _alertsUiState.emit(loadingUiState)
    }

    sealed class AlertsUiState {
        data class Success(val alerts: List<HomeAlertItem>) : AlertsUiState()
        data class Loading(val isLoading: Boolean) : AlertsUiState()
    }
}