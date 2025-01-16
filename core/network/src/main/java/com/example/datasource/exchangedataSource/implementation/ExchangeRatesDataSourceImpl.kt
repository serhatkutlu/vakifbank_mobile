package com.example.datasource.exchangedataSource.implementation

import com.example.datasource.exchangedataSource.abstraction.ExchangeRatesDataSource
import com.example.dto.exchangerates.response.ExchangeRates
import com.example.service.ExchangeRatesService
import retrofit2.Response
import javax.inject.Inject

class ExchangeRatesDataSourceImpl @Inject constructor(private val exchangeRatesService: ExchangeRatesService) :
    ExchangeRatesDataSource {
    override suspend fun getExchangeRatesData(): Response<ExchangeRates> =
        exchangeRatesService.getExchangeRates()

}