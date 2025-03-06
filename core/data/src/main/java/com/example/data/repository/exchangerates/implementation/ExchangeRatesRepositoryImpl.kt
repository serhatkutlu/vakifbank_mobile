package com.example.data.repository.exchangerates.implementation

import com.example.data.mapper.toRateUiData
import com.example.data.repository.BaseRepository
import com.example.common.resource.Resource
import com.example.common.resource.transform
import com.example.datasource.exchangedatasource.abstraction.ExchangeRatesDataSource
import com.example.domain.model.exchangerate.MarketData
import com.example.domain.repository.abstraction.ExchangeRatesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ExchangeRatesRepositoryImpl @Inject constructor(private val exchangeRatesDataSource: ExchangeRatesDataSource):
    ExchangeRatesRepository, BaseRepository() {
    override suspend fun getExchangeRatesData(): Flow<Resource<List<MarketData>>> {
        return safeApiCall {
            exchangeRatesDataSource.getExchangeRatesData()
        }.map { resource ->
            resource.transform {
                Resource.Success(it?.rates?.toRateUiData())
            }
        }
    }
}