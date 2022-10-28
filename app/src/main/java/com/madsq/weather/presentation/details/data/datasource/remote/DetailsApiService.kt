package com.madsq.weather.presentation.details.data.datasource.remote

import com.madsq.weather.presentation.details.data.model.AffectedZoneResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url


/**
 * Created by mihai.adascalitei on 25.10.2022.
 */

interface DetailsApiService {

    @GET
    suspend fun getAffectedZone(
        @Url url: String
    ): Response<AffectedZoneResponse>
}