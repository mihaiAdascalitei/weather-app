package com.madsq.weather.presentation.details.data.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable


/**
 * Created by mihai.adascalitei on 28.10.2022.
 */

data class AffectedZoneResponse(
    @SerializedName("id") val id: String? = null,
    @SerializedName("properties") val properties: AffectedZoneProperty? = null
) : Serializable

data class AffectedZoneProperty(
    @SerializedName("id") val id: String? = null,
    @SerializedName("name") val name: String? = null
) : Serializable