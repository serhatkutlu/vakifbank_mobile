package com.example.interceptor

import com.example.BuildConfig
import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class ApiKeyInterceptor @Inject constructor():Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val requestBuilder = request.newBuilder()
        val key = BuildConfig.API_KEY


        val url: HttpUrl = request.url.newBuilder()
            .addQueryParameter("app_id", key)
            .build()
        requestBuilder.url(url)
        val newRequest = requestBuilder.build()
        return try {
            chain.proceed(newRequest)
        } catch (e: Exception) {
            throw e
        }

    }
}