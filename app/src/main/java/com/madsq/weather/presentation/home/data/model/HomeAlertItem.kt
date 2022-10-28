package com.madsq.weather.presentation.home.data.model

import androidx.core.text.HtmlCompat.fromHtml
import androidx.core.text.htmlEncode
import androidx.core.text.parseAsHtml
import com.madsq.weather.presentation.home.utils.formatDate
import java.io.Serializable


/**
 * Created by mihai.adascalitei on 25.10.2022.
 */

data class HomeAlertItem(
    val id: String,
    val areaDesc: String,
    val senderName: String,
    val sent: String,
    val end: String,
    val status: String,
    val event: String,
    val severity: String,
    val certainty: String,
    val urgency: String,
    val imageUrl: String,
    val description: CharSequence,
    val instruction: String,
    val affectedZones: List<String>
) : Serializable {
    companion object {
        fun createAll(response: AlertResponse) = response.features?.map {
            HomeAlertItem(
                id = it.id.orEmpty(),
                senderName = it.properties?.senderName.orEmpty(),
                sent = it.properties?.sent.formatDate(),
                end = it.properties?.ends.formatDate(),
                status = it.properties?.status.orEmpty(),
                event = it.properties?.event.orEmpty(),
                severity = it.properties?.severity.orEmpty(),
                areaDesc = it.properties?.areaDesc.orEmpty(),
                certainty = it.properties?.certainty.orEmpty(),
                urgency = it.properties?.urgency.orEmpty(),
                description = it.properties?.description.orEmpty().parseAsHtml(),
                instruction = it.properties?.instruction.orEmpty(),
                affectedZones = it.properties?.affectedZones.orEmpty(),
                imageUrl = ""

            )
        }.orEmpty()
    }

    val severityColor: Int
        get() = SeverityLevel.from(severity)

    override fun equals(other: Any?): Boolean =
        if (other is HomeAlertItem) {
            id == other.id
        } else false

    override fun hashCode() = run {
        var result = id.hashCode()
        result = result * 31 + imageUrl.hashCode()
        result
    }
}