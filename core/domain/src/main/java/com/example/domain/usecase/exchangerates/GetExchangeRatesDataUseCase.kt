package com.example.domain.usecase.exchangerates

import com.example.common.resource.Resource
import com.example.domain.model.exchangerate.RateUiData
import com.example.domain.repository.abstraction.ExchangeRatesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetExchangeRatesDataUseCase @Inject constructor(private val exchangeRatesRepository: ExchangeRatesRepository) {

    operator suspend fun invoke() : Flow<Resource<RateUiData>> {
        return exchangeRatesRepository.getExchangeRatesData()
    }
}
