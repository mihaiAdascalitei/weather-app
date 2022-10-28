package com.madsq.weather.presentation.details.data.model

import java.io.Serializable


/**
 * Created by mihai.adascalitei on 28.10.2022.
 */

data class ExpandableItem(
    val title: String,
    val data: CharSequence
) : Serializable