package com.madsq.weather.presentation.details.data.model

import java.io.Serializable


/**
 * Created by mihai.adascalitei on 28.10.2022.
 */

data class AffectedZoneItem(
    val id: String,
    val name: String
) : Serializable {

    companion object {
        fun from(affectedZoneResponse: AffectedZoneResponse) = AffectedZoneItem(
            id = affectedZoneResponse.properties?.id.orEmpty(),
            name = affectedZoneResponse.properties?.name.orEmpty()
        )
    }
}

fun AffectedZoneItem?.orEmpty() = this ?: AffectedZoneItem(id = "", name = "")