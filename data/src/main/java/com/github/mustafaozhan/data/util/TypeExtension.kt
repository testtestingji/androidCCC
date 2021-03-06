/*
 Copyright (c) 2020 Mustafa Ozhan. All rights reserved.
 */
package com.github.mustafaozhan.data.util

import android.annotation.SuppressLint
import com.github.mustafaozhan.data.model.CurrencyResponse
import com.github.mustafaozhan.data.model.Rates
import com.github.mustafaozhan.scopemob.mapTo
import com.github.mustafaozhan.scopemob.whether
import com.github.mustafaozhan.scopemob.whetherNot
import timber.log.Timber
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

private const val DATE_FORMAT = "HH:mm MM.dd.yyyy"

fun String.toStandardDigits(): String {
    val builder = StringBuilder()
    forEach { char ->
        char.whether { Character.isDigit(it) }
            ?.whetherNot { it in '0'..'9' }
            ?.mapTo { Character.getNumericValue(it) }
            ?.whether { it >= 0 }
            ?.let { builder.append(it) }
            ?: run { builder.append(char) }
    }
    return builder.toString()
}

fun String.toSupportedCharacters() =
    replace(",", ".")
        .replace("٫", ".")
        .replace(" ", "")
        .replace("−", "-")

fun String.toPercent() = replace("%", "/100*")

fun Double.getFormatted(): String {
    val symbols = DecimalFormatSymbols.getInstance()
    symbols.groupingSeparator = ' '
    return DecimalFormat("###,###.###", symbols).format(this)
}

fun String.dropDecimal() = replace(" ", "").let { nonEmpty ->
    nonEmpty.whether { contains(".") }
        ?.substring(0, nonEmpty.indexOf("."))
        ?: run { nonEmpty }
}

fun Date.dateStringToFormattedString(): String = SimpleDateFormat(DATE_FORMAT, Locale.ENGLISH).format(this)

fun CurrencyResponse.toRate(): Rates {
    val rate = rates
    rate.base = base
    // todo need to change to CurrencyResponse.date when BE return date
    rate.date = Date().dateStringToFormattedString()
    return rate
}

@SuppressLint("DefaultLocale")
inline fun <reified T> Any.getThroughReflection(propertyName: String): T? {
    val getterName = "get" + propertyName.capitalize()
    return try {
        javaClass.getMethod(getterName).invoke(this) as? T
    } catch (e: NoSuchMethodException) {
        Timber.e(e)
        null
    }
}
