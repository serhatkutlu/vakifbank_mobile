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
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {


    @Provides
    @Singleton
    @Named("baseRetrofit")
    fun provideRetrofit(apiKeyInterceptor: ApiKeyInterceptor): Retrofit =
        RetrofitFactory(apiKeyInterceptor).createRetrofit(BuildConfig.BASE_URL)

     @Provides
    @Singleton
     @Named("StoryRetrofit")
    fun provideStoryUrlRetrofit(): Retrofit =
        RetrofitFactory().createRetrofit(BuildConfig.STORY_BASE_URL)




    @Provides
    @Singleton
    fun provideExchangeRatesService(@Named("baseRetrofit")retrofit: Retrofit): ExchangeRatesService =retrofit.create()

    @Provides
    @Singleton
    fun provideStoryService(@Named("StoryRetrofit")retrofit: Retrofit): StoryService =retrofit.create()
}