package com.madsq.weather.presentation.settings.utils

import android.graphics.Typeface
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.StyleSpan
import java.util.regex.Pattern


/**
 * Created by mihai.adascalitei on 28.10.2022.
 */

fun String.computeMagicText(): CharSequence {
    var spannedMagicText = createMagicText(MagicTextSymbol.BOLD)
    spannedMagicText = spannedMagicText.createMagicText(MagicTextSymbol.ITALIC)
    spannedMagicText = spannedMagicText.createMagicText(MagicTextSymbol.NONE)
    return spannedMagicText
}

fun CharSequence.createMagicText(symbol: MagicTextSymbol): Spannable {
    val spannableText = SpannableStringBuilder(this)
    val symbolPositions = Regex(symbol.pattern).findAll(this).map {
        it.range.first
    }.makeEven()

    symbolPositions.chunked(2).forEach { (start, end) ->
        if (symbol == MagicTextSymbol.NONE) {
            spannableText.replace(start, end + 1, "")
        } else {
            spannableText.setSpan(
                StyleSpan(symbol.typeface),
                start,
                end,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
        }
    }
    spannableText.removeLeftovers(symbol.pattern)

    return spannableText
}

fun SpannableStringBuilder.removeLeftovers(symbol: String) {
    val pattern = Pattern.compile(symbol)
    var matcher = pattern.matcher(this)
    while (matcher.find()) {
        val start = matcher.start()
        val end = matcher.end()
        replace(start, end, "")
        matcher = pattern.matcher(this)
    }
}

fun Sequence<Int>.makeEven() =
    toMutableList().run {
        if (size % 2 == 0) {
            this
        } else {
            this.apply { removeLast() }
        }
    }

enum class MagicTextSymbol(val pattern: String, val typeface: Int) {
    BOLD("@", Typeface.BOLD),
    ITALIC("\\$", Typeface.ITALIC),
    NONE("#", Typeface.NORMAL)
}