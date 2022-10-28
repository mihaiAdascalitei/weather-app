package com.madsq.weather.presentation.details.data.datasource.remote

import com.madsq.weather.network.apiCall
import com.madsq.weather.network.map
import com.madsq.weather.presentation.details.data.datasource.DetailsDataSource
import com.madsq.weather.presentation.details.data.model.AffectedZoneItem
import com.madsq.weather.presentation.details.data.model.orEmpty


/**
 * Created by mihai.adascalitei on 25.10.2022.
 */

class DetailsRemoteDataSource(
    private val apiService: DetailsApiService
) : DetailsDataSource {
    companion object {
        private var INSTANCE: DetailsRemoteDataSource? = null

        fun getInstance(
            apiService: DetailsApiService
        ) = INSTANCE ?: synchronized(this) {
            INSTANCE ?: DetailsRemoteDataSource(apiService).also {
                INSTANCE = it
            }
        }
    }

    override suspend fun getAffectedZone(url: String) =
        apiCall { apiService.getAffectedZone(url) }.map {
            AffectedZoneItem.from(it)
        }.orEmpty()
}