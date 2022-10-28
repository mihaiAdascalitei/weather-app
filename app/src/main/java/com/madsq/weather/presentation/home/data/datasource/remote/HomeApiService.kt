package com.madsq.weather.presentation.home.data.datasource.remote

import com.madsq.weather.presentation.home.data.model.AlertResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.QueryMap


/**
 * Created by mihai.adascalitei on 25.10.2022.
 */

interface HomeApiService {
    @GET("alerts/active")
    suspend fun getAlerts(@QueryMap params: Map<String, String>): Response<AlertResponse>

    @GET("https://picsum.photos/1000")
    suspend fun getPics(): Response<Unit>
}