package com.madsq.weather.network


/**
 * Created by mihai.adascalitei on 24.10.2022.
 */

import okhttp3.HttpUrl
import java.net.HttpURLConnection

sealed class Result<out T : Any> {

    data class Success<out T : Any>(val data: T, val url: HttpUrl? = null) : Result<T>()

    data class Error(
        val message: String? = "Request failed",
        val code: Int = HttpURLConnection.HTTP_BAD_REQUEST
    ) : Result<Nothing>()
}

fun <T : Any, M> Result<T>.map(mapper: (T) -> M): M? = run {
    if (this is Result.Success) {
        mapper(data)
    } else null
}

fun <T : Any, M> Result<T>.mapSuccess(mapper: (Result.Success<T>) -> M): M? = run {
    if (this is Result.Success) {
        mapper(this)
    } else null
}