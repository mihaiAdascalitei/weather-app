package com.madsq.weather.presentation.home.utils


/**
 * Created by mihai.adascalitei on 25.10.2022.
 */

fun alertsQueryParams() = mutableMapOf<String, String>().apply {
    put("status", "actual")
    put("message_type", "alert")
}

