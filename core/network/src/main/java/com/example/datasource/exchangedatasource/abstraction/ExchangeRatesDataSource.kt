package com.example.datasource.exchangedatasource.abstraction

import com.example.dto.exchangerates.response.ExchangeRates
import retrofit2.Response

interface ExchangeRatesDataSource {

    suspend fun getExchangeRatesData():Response<ExchangeRates>
}