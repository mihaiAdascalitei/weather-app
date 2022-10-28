package com.madsq.weather.presentation.details.data.datasource

import com.madsq.weather.presentation.details.data.model.AffectedZoneItem


/**
 * Created by mihai.adascalitei on 25.10.2022.
 */

interface DetailsDataSource {
    suspend fun getAffectedZone(url: String): AffectedZoneItem
}