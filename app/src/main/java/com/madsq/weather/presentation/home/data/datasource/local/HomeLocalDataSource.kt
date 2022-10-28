package com.madsq.weather.presentation.home.data.datasource.local

import com.madsq.weather.presentation.home.data.datasource.HomeDataSource
import com.madsq.weather.presentation.home.data.model.HomeAlertItem

/**
 * Created by mihai.adascalitei on 25.10.2022.
 */

class HomeLocalDataSource : HomeDataSource {

    companion object {
        private var INSTANCE: HomeLocalDataSource? = null

        fun getInstance() = INSTANCE ?: synchronized(this) {
            INSTANCE ?: HomeLocalDataSource().also {
                INSTANCE = it
            }
        }
    }

    override suspend fun getAlerts(params: Map<String, String>): List<HomeAlertItem> = emptyList()

    override suspend fun saveAlerts(alerts: List<HomeAlertItem>) {
        TODO("Not yet implemented")
    }

    override suspend fun getPics(count: Int): List<String> {
        TODO("Not yet implemented")
    }
}