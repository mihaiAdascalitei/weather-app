package com.madsq.weather.presentation.home.data.datasource

import com.madsq.weather.presentation.home.data.model.HomeAlertItem


/**
 * Created by mihai.adascalitei on 25.10.2022.
 */

class HomeRepository(
    private val localDs: HomeDataSource,
    private val remoteDs: HomeDataSource
) : HomeDataSource {

    companion object {
        private var INSTANCE: HomeRepository? = null

        fun getInstance(
            localDs: HomeDataSource,
            remoteDs: HomeDataSource
        ) = INSTANCE ?: synchronized(this) {
            INSTANCE ?: HomeRepository(localDs, remoteDs).also {
                INSTANCE = it
            }
        }
    }

    private var uniquePictures = mutableListOf<String>()

    override suspend fun getAlerts(params: Map<String, String>) =
        remoteDs.getAlerts(params)

    override suspend fun saveAlerts(alerts: List<HomeAlertItem>) = localDs.saveAlerts(alerts)

    override suspend fun getPics(count: Int) = run {
        val currentPics = mutableListOf<String>()
        while (currentPics.size < count) {
            remoteDs.getPics(count).also { urls ->
                if (uniquePictures.any { it == urls[0] }.not()) {
                    currentPics += urls
                }
            }
        }
        uniquePictures += currentPics
        currentPics
    }
}