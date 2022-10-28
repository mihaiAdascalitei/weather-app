package com.madsq.weather.network


/**
 * Created by mihai.adascalitei on 24.10.2022.
 */

import okhttp3.HttpUrl
import java.net.HttpURLConnection

sealed class Result<out T : Any> { //the class that handles the request interpretation

    data class Success<out T : Any>(val data: T, val url: HttpUrl? = null) : Result<T>()

    data class Error(
        val message: String? = "Request failed",
        val code: Int = HttpURLConnection.HTTP_BAD_REQUEST
    ) : Result<Nothing>()
}

//map the result after getting the data from the API
fun <T : Any, M> Result<T>.map(mapper: (T) -> M): M? = run {
    if (this is Result.Success) {
        mapper(data)
    } else null
}

//same function but returns the Success
fun <T : Any, M> Result<T>.mapSuccess(mapper: (Result.Success<T>) -> M): M? = run {
    if (this is Result.Success) {
        mapper(this)
    } else null
}