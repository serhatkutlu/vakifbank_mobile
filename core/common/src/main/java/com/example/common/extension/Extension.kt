package com.example.common.extension

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Configuration
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import com.example.common.helper.LocaleHelper
import org.intellij.lang.annotations.Language
import java.util.Locale

object Extension {

    @SuppressLint("MissingPermission")
    fun Context.isInternetAvailable(): Boolean {
        val connectivityManager =
            getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: Network? = connectivityManager.activeNetwork
        val networkCapabilities = connectivityManager.getNetworkCapabilities(activeNetwork)

        return networkCapabilities?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) == true
    }

    fun Context.changeAppLanguage(languageCode: Language) {
        LocaleHelper.setLocale(this, languageCode.code)
    }

    fun Context.getAppLanguage(): Language {
        val locale: Locale = this.resources.configuration.locale
        return when (locale.language) {
            "en" -> Language.ENGLISH
            "tr" -> Language.TURKISH
            else -> Language.ENGLISH
        }

    }


    enum class Language(val code: String) {
        ENGLISH("en"),
        TURKISH("tr");

        fun getNextLanguage(): Language {
            return when (this) {
                ENGLISH -> TURKISH
                TURKISH -> ENGLISH
            }
        }
    }
}