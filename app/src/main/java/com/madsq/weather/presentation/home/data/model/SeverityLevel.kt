package com.madsq.weather.presentation.home.data.model

import com.madsq.weather.R


/**
 * Created by mihai.adascalitei on 26.10.2022.
 */

enum class SeverityLevel {
    Minor,
    Moderate,
    Severe;

    companion object {
        fun from(severityLabel: String) = when (severityLabel) {
            Moderate.name -> R.color.misc_yellow
            Severe.name -> R.color.misc_red
            else -> R.color.misc_green
        }
    }
}