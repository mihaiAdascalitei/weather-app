package com.madsq.weather.presentation.settings.utils

import android.text.Spannable
import android.text.SpannableStringBuilder


/**
 * Created by mihai.adascalitei on 28.10.2022.
 */

fun String.computeMagicText(): Spannable {
    return SpannableStringBuilder(this)
}