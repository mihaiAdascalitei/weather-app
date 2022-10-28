package com.madsq.weather.presentation.home.data.datasource

import com.madsq.weather.presentation.home.data.model.HomeAlertItem

/**
 * Created by mihai.adascalitei on 25.10.2022.
 */

interface HomeDataSource {

    suspend fun getAlerts(params: Map<String, String>): List<HomeAlertItem>
    suspend fun saveAlerts(alerts: List<HomeAlertItem>)
    suspend fun getPics(count: Int): List<String>
}