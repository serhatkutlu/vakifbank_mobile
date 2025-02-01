package com.example.data.di

import com.example.domain.repository.abstraction.ExchangeRatesRepository
import com.example.data.repository.exchangerates.implementation.ExchangeRatesRepositoryImpl
import com.example.data.repository.story.StoryRepositoryImpl
import com.example.domain.repository.abstraction.StoryRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
interface  RepositoryModule {

    @Binds
     fun bindExchangeRatesRepository(exchangeRatesRepositoryImpl: ExchangeRatesRepositoryImpl): ExchangeRatesRepository
    @Binds
     fun bindStoryRepository(storyRepositoryImpl: StoryRepositoryImpl): StoryRepository

}