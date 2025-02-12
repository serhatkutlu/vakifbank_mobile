package com.example.common.helper
import android.content.Context
import android.content.res.Configuration
import android.os.Build
import android.os.LocaleList
import androidx.appcompat.app.AppCompatDelegate
import com.example.common.extension.Extension
import java.util.Locale

object LocaleHelper {

    fun setLocale(context: Context, languageCode: String) {
        val locale = Locale(languageCode)
        Locale.setDefault(locale)

        val config = Configuration(context.resources.configuration)
        config.setLocale(locale)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            val localeList = LocaleList(locale)
            LocaleList.setDefault(localeList)
            config.setLocales(localeList)
            context.createConfigurationContext(config)
        }

        context.resources.updateConfiguration(config, context.resources.displayMetrics)
        AppCompatDelegate.setApplicationLocales(androidx.core.os.LocaleListCompat.forLanguageTags(languageCode))
    }

    fun getAppLanguage(context: Context): Extension.Language {
        val locale: Locale = context.resources.configuration.locales[0]
        return when (locale.language) {
            "en" -> Extension.Language.ENGLISH
            "tr" -> Extension.Language.TURKISH
            else -> Extension.Language.ENGLISH
        }
    }
}