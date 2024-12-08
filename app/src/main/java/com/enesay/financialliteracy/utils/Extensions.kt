package com.enesay.financialliteracy.utils

import android.content.Context
import java.util.Locale

fun Context.setAppLocale(language: String): Context {
    val locale = Locale(language)
    Locale.setDefault(locale)
    val config = resources.configuration
    config.setLocale(locale)
    config.setLayoutDirection(locale)

    return createConfigurationContext(config)
}
