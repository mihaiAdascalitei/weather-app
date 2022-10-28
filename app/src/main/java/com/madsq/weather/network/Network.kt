package com.madsq.weather.network


/**
 * Created by mihai.adascalitei on 24.10.2022.
 */


import retrofit2.Response

suspend fun <T : Any> apiCall(
    call: suspend () -> Response<T>
): Result<T> =
    try {
        call().run {
            if (isSuccessful) {
                body()?.let {
                    Result.Success(it, raw().request.url)
                } ?: Result.Error()
            } else {
                Result.Error(message = errorBody()?.string())
            }
        }
    } catch (e: java.lang.Exception) {
        Result.Error(message = e.message)
    }
