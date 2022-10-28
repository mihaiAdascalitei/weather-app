package com.madsq.weather.presentation.home.data.datasource.remote

import com.madsq.weather.network.apiCall
import com.madsq.weather.network.map
import com.madsq.weather.network.mapSuccess
import com.madsq.weather.presentation.home.data.datasource.HomeDataSource
import com.madsq.weather.presentation.home.data.model.HomeAlertItem


/**
 * Created by mihai.adascalitei on 25.10.2022.
 */

class HomeRemoteDataSource(
    private val apiService: HomeApiService
) : HomeDataSource {

    companion object {
        private var INSTANCE: HomeRemoteDataSource? = null

        fun getInstance(
            apiService: HomeApiService
        ) = INSTANCE ?: synchronized(this) {
            INSTANCE ?: HomeRemoteDataSource(apiService).also {
                INSTANCE = it
            }
        }
    }

    override suspend fun getAlerts(params: Map<String, String>) = apiCall {
        apiService.getAlerts(params)
    }.map { HomeAlertItem.createAll(it) }.orEmpty()

    override suspend fun saveAlerts(alerts: List<HomeAlertItem>) {
        throw NotImplementedError("Not for remote")
    }

    override suspend fun getPics(count: Int) =
        apiCall { apiService.getPics() }.mapSuccess { listOf(it.url.toString()) }.orEmpty()
}