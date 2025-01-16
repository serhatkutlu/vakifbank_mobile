package com.example.di

import com.example.datasource.exchangedataSource.abstraction.ExchangeRatesDataSource
import com.example.datasource.exchangedataSource.implementation.ExchangeRatesDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
abstract class DataSourceModule {

    @Singleton
    @Binds
    abstract fun bindExchangeRatesDataSource(exchangeRatesDataSourceImpl: ExchangeRatesDataSourceImpl): ExchangeRatesDataSource
}