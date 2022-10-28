package com.madsq.weather.presentation.details.data.datasource.local

import com.madsq.weather.presentation.details.data.datasource.DetailsDataSource
import com.madsq.weather.presentation.details.data.model.AffectedZoneItem


/**
 * Created by mihai.adascalitei on 25.10.2022.
 */

class DetailsLocalDataSource : DetailsDataSource {
    companion object {
        private var INSTANCE: DetailsLocalDataSource? = null

        fun getInstance() = INSTANCE ?: synchronized(this) {
            INSTANCE ?: DetailsLocalDataSource().also {
                INSTANCE = it
            }
        }
    }

    override suspend fun getAffectedZone(url: String): AffectedZoneItem {
        TODO("Not yet implemented")
    }
}