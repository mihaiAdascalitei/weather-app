package com.madsq.weather.injection

import android.content.Context
import android.preference.PreferenceManager
import com.madsq.weather.network.RetrofitBuilder
import com.madsq.weather.presentation.details.data.datasource.DetailsDataSource
import com.madsq.weather.presentation.details.data.datasource.DetailsRepository
import com.madsq.weather.presentation.details.data.datasource.local.DetailsLocalDataSource
import com.madsq.weather.presentation.details.data.datasource.remote.DetailsApiService
import com.madsq.weather.presentation.details.data.datasource.remote.DetailsRemoteDataSource
import com.madsq.weather.presentation.home.data.datasource.HomeDataSource
import com.madsq.weather.presentation.home.data.datasource.HomeRepository
import com.madsq.weather.presentation.home.data.datasource.local.HomeLocalDataSource
import com.madsq.weather.presentation.home.data.datasource.remote.HomeApiService
import com.madsq.weather.presentation.home.data.datasource.remote.HomeRemoteDataSource


/**
 * Created by mihai.adascalitei on 25.10.2022.
 */

//manual injection - not using extra libraries like hilt or koin

//retrofit
fun provideRetrofit() = RetrofitBuilder.build()

//home
fun provideHomeApiService(): HomeApiService = provideRetrofit().create(HomeApiService::class.java)
fun provideHomeRemoteDataSource(): HomeDataSource =
    HomeRemoteDataSource.getInstance(provideHomeApiService())

fun provideHomeLocalDataSource(): HomeDataSource = HomeLocalDataSource.getInstance()
fun provideHomeRepository(): HomeDataSource = HomeRepository.getInstance(
    localDs = provideHomeLocalDataSource(),
    remoteDs = provideHomeRemoteDataSource()
)

//details
fun provideDetailsApiService(): DetailsApiService =
    provideRetrofit().create(DetailsApiService::class.java)

fun provideDetailsRemoteDataSource(): DetailsDataSource = DetailsRemoteDataSource.getInstance(
    provideDetailsApiService()
)

fun provideDetailsLocalDataSource(): DetailsDataSource = DetailsLocalDataSource.getInstance()
fun provideDetailsRepository(): DetailsDataSource = DetailsRepository.getInstance(
    localDs = provideDetailsLocalDataSource(),
    remoteDs = provideDetailsRemoteDataSource()
)

//shared prefs
fun provideSharedPrefs(ctx: Context) =
    PreferenceManager.getDefaultSharedPreferences(ctx)
