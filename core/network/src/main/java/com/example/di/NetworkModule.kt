package com.example.di

import com.example.BuildConfig
import com.example.factory.RetrofitFactory
import com.example.interceptor.ApiKeyInterceptor
import com.example.service.ExchangeRatesService
import com.example.service.StoryService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.create
import javax.inject.Named
import javax.inject.Qualifier
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {


    @Provides
    @Singleton
    @BaseRetrofit
    fun provideRetrofit(apiKeyInterceptor: ApiKeyInterceptor): Retrofit =
        RetrofitFactory(apiKeyInterceptor).createRetrofit(BuildConfig.BASE_URL)

    @Provides
    @Singleton
    @StoryRetrofit
    fun provideStoryUrlRetrofit(): Retrofit =
        RetrofitFactory(null).createRetrofit(BuildConfig.STORY_BASE_URL)


    @Provides
    @Singleton
    fun provideExchangeRatesService(@BaseRetrofit retrofit: Retrofit): ExchangeRatesService =
        retrofit.create()

    @Provides
    @Singleton
    fun provideStoryService(@StoryRetrofit retrofit: Retrofit): StoryService = retrofit.create()


    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class BaseRetrofit

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class StoryRetrofit
}