package com.example.domain.usecase.exchangerates

import com.example.common.resource.Resource
import com.example.domain.model.exchangerate.MarketData
import com.example.domain.repository.abstraction.ExchangeRatesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetExchangeRatesDataUseCase @Inject constructor(private val exchangeRatesRepository: ExchangeRatesRepository) {

    suspend operator fun invoke() : Flow<Resource<List<MarketData>>> {
        return exchangeRatesRepository.getExchangeRatesData()
    }
}
