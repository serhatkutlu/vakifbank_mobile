package com.example.marketknowledge.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.common.resource.Resource
import com.example.common.resource.ResourceUi
import com.example.domain.model.exchangerate.ExchangeRateCategory
import com.example.domain.model.exchangerate.MarketData
import com.example.domain.usecase.exchangerates.GetExchangeRatesDataUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MarketKnowledgeViewModel @Inject constructor(private val getExchangeRatesDataUseCase: GetExchangeRatesDataUseCase) :
    ViewModel() {

    private val _state: MutableStateFlow<MarketState> =
        MutableStateFlow(MarketState(ResourceUi.Idle()))
    val state = _state.asStateFlow()

    private val _event = MutableSharedFlow<MarketEvent>()

    private val _effect = MutableSharedFlow<MarketEffect>()
    val effect = _effect.asSharedFlow()

    init {
        getExchangeRatesData()
    }

    private fun getExchangeRatesData() {
        viewModelScope.launch {
            getExchangeRatesDataUseCase().collect { excangeRateData ->
                when (excangeRateData) {

                    is Resource.Success -> {
                        val mappedData = excangeRateData.data?.groupBy { it.category }
                        val newList = mutableListOf<MarketData>()
                        mappedData?.keys?.forEach { key ->
                            val marketData = MarketData(
                                name = key.name,
                                category = ExchangeRateCategory.HEADER,
                                buy = -1.0,
                                sell = -1.0
                            )
                            newList.addAll(mutableListOf<MarketData>().apply {
                                add(marketData)
                                mappedData[key]?.let { addAll(it) }
                            })
                        }

                        _state.update { it.copy(marketData = ResourceUi.Success(newList)) }

                    }

                    is Resource.Error -> {
                        //state.update { it.copy(marketData = ResourceUi.Success(excangeRateData.error)) }
                    }

                    is Resource.Loading -> {
                        _state.update { it.copy(marketData = ResourceUi.Loading()) }
                    }
                }
            }
        }
    }
}

sealed interface MarketEvent {
    data object NextClicked : MarketEvent
    data object PreviousClicked : MarketEvent
    data object OnSwipeUp : MarketEvent
    data object OnSwipeDown : MarketEvent

}

sealed interface MarketEffect {
    data object NextPage : MarketEffect
    data object PreviousPage : MarketEffect
    data object Close : MarketEffect

}


data class MarketState(
    val marketData: ResourceUi<List<MarketData>>
)