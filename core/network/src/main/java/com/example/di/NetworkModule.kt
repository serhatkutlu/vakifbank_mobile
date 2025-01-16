package com.example.di

import com.example.BuildConfig
import com.example.factory.RetrofitFactory
import com.example.interceptor.ApiKeyInterceptor
import com.example.service.ExchangeRatesService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.create
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {


    @Provides
    @Singleton
    fun provideRetrofit(apiKeyInterceptor: ApiKeyInterceptor): Retrofit =
        RetrofitFactory(apiKeyInterceptor).createRetrofit(BuildConfig.BASE_URL)


    @Provides
    @Singleton
    fun provideExchangeRatesService(retrofit: Retrofit): ExchangeRatesService =retrofit.create()
}