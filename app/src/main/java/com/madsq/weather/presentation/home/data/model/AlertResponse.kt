package com.madsq.weather.presentation.home.data.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * Created by mihai.adascalitei on 25.10.2022.
 */

//default retrofit response handling
data class AlertResponse(
    @SerializedName("type") val type: String? = null,
    @SerializedName("title") val title: String? = null,
    @SerializedName("features") val features: List<Feature>? = null
) : Serializable

data class Feature(
    @SerializedName("id") val id: String? = null,
    @SerializedName("type") val type: String? = null,
    @SerializedName("properties") val properties: Property? = null
) : Serializable

data class Property(
    @SerializedName("id") val id: String? = null,
    @SerializedName("sent") val sent: String? = null,
    @SerializedName("ends") val ends: String? = null,
    @SerializedName("status") val status: String? = null,
    @SerializedName("senderName") val senderName: String? = null,
    @SerializedName("areaDesc") val areaDesc: String? = null,
    @SerializedName("event") val event: String? = null,
    @SerializedName("severity") val severity: String? = null,
    @SerializedName("urgency") val urgency: String? = null,
    @SerializedName("certainty") val certainty: String? = null,
    @SerializedName("description") val description: String? = null,
    @SerializedName("instruction") val instruction: String? = null,
    @SerializedName("affectedZones") val affectedZones: List<String>? = null
) : Serializable
