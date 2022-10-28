package com.madsq.weather.presentation.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.madsq.weather.injection.provideDetailsRepository
import com.madsq.weather.presentation.details.data.datasource.DetailsDataSource
import com.madsq.weather.presentation.details.data.model.AffectedZoneItem
import com.madsq.weather.presentation.home.data.model.HomeAlertItem
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch


/**
 * Created by mihai.adascalitei on 24.10.2022.
 */

class DetailsViewModel(private val alertItem: HomeAlertItem) : ViewModel() {

    private val repo: DetailsDataSource = provideDetailsRepository()
    private val _zonesUiState = MutableSharedFlow<ZonesUiState>(replay = 1)
    val zonesUiState: SharedFlow<ZonesUiState> = _zonesUiState.asSharedFlow()

    init {
        getAffectedZones()
    }

    private fun getAffectedZones() {
        viewModelScope.launch {
            alertItem.affectedZones.map {
                async { repo.getAffectedZone(it) }
            }.awaitAll().also { //requesting them in parallel
                _zonesUiState.emit(ZonesUiState.Success(it))
            }
        }
    }

    sealed class ZonesUiState {
        data class Success(val zones: List<AffectedZoneItem>) : ZonesUiState()
    }
}