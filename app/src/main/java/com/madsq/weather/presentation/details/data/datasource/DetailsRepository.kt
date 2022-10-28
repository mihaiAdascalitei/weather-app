package com.madsq.weather.presentation.details.data.datasource

import com.madsq.weather.presentation.details.data.model.AffectedZoneItem


/**
 * Created by mihai.adascalitei on 25.10.2022.
 */

class DetailsRepository(
    private val localDs: DetailsDataSource,
    private val remoteDs: DetailsDataSource
) : DetailsDataSource {
    companion object {
        private var INSTANCE: DetailsRepository? = null

        fun getInstance(
            localDs: DetailsDataSource,
            remoteDs: DetailsDataSource
        ) = INSTANCE ?: synchronized(this) {
            INSTANCE ?: DetailsRepository(localDs, remoteDs).also {
                INSTANCE = it
            }
        }
    }

    override suspend fun getAffectedZone(url: String) = remoteDs.getAffectedZone(url)
}