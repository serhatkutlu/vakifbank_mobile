package com.example.marketknowledge.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.common.resource.Resource
import com.example.common.resource.ResourceUi
import com.example.common.resource.UiText
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
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
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
        collectEvent()
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

                        _state.update { it.copy(marketData = ResourceUi.Success(newList), lastUpdateDate = getDate()) }

                    }

                    is Resource.Error -> {
                        _effect.emit(MarketEffect.showError(excangeRateData.error))
                    }

                    is Resource.Loading -> {
                        _state.update { it.copy(marketData = ResourceUi.Loading()) }
                    }
                }
            }
        }
    }
    fun setEvent(event: MarketEvent) {
        viewModelScope.launch {
            _event.emit(event)
        }
    }
    private fun collectEvent() {
        viewModelScope.launch {
            _event.collect {
                when(it){
                    is MarketEvent.ClickRefresh->{
                        getExchangeRatesData()
                    }
                }
            }
            }
    }
    private fun getDate():String{
        val calendar = Calendar.getInstance()
        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        val formatted = dateFormat.format(calendar.time)
        return formatted
    }
}

sealed interface MarketEvent {
    data object ClickRefresh : MarketEvent

}

sealed interface MarketEffect {
    data class showError(val message: UiText?) : MarketEffect


}


data class MarketState(
    val marketData: ResourceUi<List<MarketData>>,
    var lastUpdateDate: String = ""
)