package com.madsq.weather.presentation.home.utils

import java.text.SimpleDateFormat
import java.util.*


/**
 * Created by mihai.adascalitei on 27.10.2022.
 */

private val apiDateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX", Locale.US)
private val resultDateFormat = SimpleDateFormat("EEE, d MMM HH:mm", Locale.US)

fun String?.formatDate() = this?.let { date ->
    apiDateFormat.parse(date)?.let {
        resultDateFormat.format(it)
    }
}.orEmpty()