package com.example.di

import com.example.datasource.exchangedatasource.abstraction.ExchangeRatesDataSource
import com.example.datasource.exchangedatasource.implementation.ExchangeRatesDataSourceImpl
import com.example.datasource.storydatasource.abstraction.StoryDataSource
import com.example.datasource.storydatasource.implementation.StoryDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
abstract class DataSourceModule {

    @Binds
    abstract fun bindExchangeRatesDataSource(exchangeRatesDataSourceImpl: ExchangeRatesDataSourceImpl): ExchangeRatesDataSource
    @Binds
    abstract fun bindStoryDataSource(storyDataSourceImpl: StoryDataSourceImpl): StoryDataSource
}