package com.example.data.di

import com.example.domain.repository.abstraction.ExchangeRatesRepository
import com.example.data.repository.exchangerates.implementation.ExchangeRatesRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindExchangeRatesRepository(exchangeRatesRepositoryImpl: ExchangeRatesRepositoryImpl): com.example.domain.repository.abstraction.ExchangeRatesRepository

}