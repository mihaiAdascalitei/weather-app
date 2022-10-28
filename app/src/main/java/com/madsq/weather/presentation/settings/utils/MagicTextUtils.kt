package com.madsq.weather.presentation.settings.utils

import android.graphics.Typeface
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.StyleSpan
import java.util.regex.Pattern


/**
 * Created by mihai.adascalitei on 28.10.2022.
 */

fun String.computeMagicText(): CharSequence { //call each computation for every symbol
    var spannedMagicText = createMagicText(MagicTextSymbol.BOLD)
    spannedMagicText = spannedMagicText.createMagicText(MagicTextSymbol.ITALIC)
    spannedMagicText = spannedMagicText.createMagicText(MagicTextSymbol.NONE)
    return spannedMagicText
}

fun CharSequence.createMagicText(symbol: MagicTextSymbol): Spannable {
    val spannableText = SpannableStringBuilder(this)
    val symbolPositions =
        Regex(symbol.pattern).findAll(this).map { //find all the occurrences of the current pattern
            it.range.first
        }.makeEven()

    symbolPositions.chunked(2)
        .forEach { (start, end) -> //pair two by two to have the start and end for the inner data to be spanned
            if (symbol == MagicTextSymbol.NONE) { // remove full range
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
    spannableText.removeLeftovers(symbol.pattern) // I have to remove the symbols after the inner data was spanned,
    // cannot remove them before spanning because the start and end of the string will be modified

    return spannableText
}

fun SpannableStringBuilder.removeLeftovers(symbol: String) { //spannablestring doesn't have replace(string, newvalue) , only range
    val pattern = Pattern.compile(symbol)
    var matcher = pattern.matcher(this)
    while (matcher.find()) { // I try to find if there are any symbols
        val start = matcher.start()
        val end = matcher.end()
        replace(start, end, "")
        matcher = pattern.matcher(this)
        // I have to update the matcher because the position are changing while removing (replacing with "" )
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