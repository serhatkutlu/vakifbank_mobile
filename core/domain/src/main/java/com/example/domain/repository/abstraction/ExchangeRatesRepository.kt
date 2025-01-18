package com.example.domain.repository.abstraction


import com.example.common.resource.Resource
import com.example.domain.model.exchangerate.RateUiData
import kotlinx.coroutines.flow.Flow

interface ExchangeRatesRepository {
    suspend fun getExchangeRatesData(): Flow<Resource<RateUiData>>
}