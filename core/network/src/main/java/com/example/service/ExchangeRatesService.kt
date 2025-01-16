package com.example.service

import com.example.dto.exchangerates.response.ExchangeRates
import com.example.endpoint.exchangeRatesEndpoint
import retrofit2.Response
import retrofit2.http.GET

interface ExchangeRatesService {

    @GET(exchangeRatesEndpoint.path)
    suspend fun getExchangeRates(
    ): Response<ExchangeRates>

}