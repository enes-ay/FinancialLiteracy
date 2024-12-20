package com.enesay.financialliteracy.utils

import android.content.Context
import android.content.ContextWrapper
import android.content.res.Configuration
import android.os.LocaleList
import java.util.Locale

fun setAppLocale(context: Context, language: String) {
    val locale = Locale(language)
    Locale.setDefault(locale)
    val config = Configuration(context.resources.configuration)
    config.setLocale(locale)
    context.resources.updateConfiguration(config, context.resources.displayMetrics)
}

fun Context.wrapLocale(newLocale: Locale): Context {
    val configuration = Configuration(resources.configuration)
    configuration.setLocale(newLocale)
    configuration.setLocales(LocaleList(newLocale))

    return ContextWrapper(createConfigurationContext(configuration))
}
