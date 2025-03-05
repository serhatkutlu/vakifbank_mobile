package com.example.data.mapper

import com.example.data.util.calculateSpread
import com.example.data.util.Constants
import com.example.domain.model.exchangerate.ExchangeRateCategory
import com.example.domain.model.exchangerate.MarketData
import com.example.dto.exchangerates.response.Rates


fun Rates.toRateUiData(): List<MarketData> {

    val marketDataList:MutableList<MarketData> = mutableListOf()

    TRY.calculateSpread(2.2f).let {
        marketDataList.add(MarketData("USD",ExchangeRateCategory.FOREIGN_CURRENCY,it.first,it.second))
    }
    calculateForeignCurrency(EUR, TRY).calculateSpread(2.2f).let {
        marketDataList.add(MarketData("EUR",ExchangeRateCategory.FOREIGN_CURRENCY,it.first,it.second))

    }
    calculateForeignCurrency(GBP, TRY).calculateSpread(2.2f).let {
        marketDataList.add(MarketData("GBP",ExchangeRateCategory.FOREIGN_CURRENCY,it.first,it.second))
    }
    calculateForeignCurrency(EUR, USD).calculateSpread(0.2f).let {
        marketDataList.add(MarketData("EUR/USD",ExchangeRateCategory.EXCHANGE_PAIR,it.first,it.second))
    }

    calculatePreciousMetals(TRY, XAG).calculateSpread(2.2f).let {
        marketDataList.add(MarketData("XAG/TL",ExchangeRateCategory.PRECIOUS_METAL,it.first,it.second))
    }

    calculatePreciousMetals(TRY, XAU).calculateSpread(2.2f).let {
        marketDataList.add(MarketData("ALT/TL",ExchangeRateCategory.PRECIOUS_METAL,it.first,it.second))
    }
    return marketDataList



}


private fun calculatePreciousMetals(tl: Double?, value: Double?):Double?{
    return value?.let {tl?.div(it) }?.div(Constants.GAU_VALUE)
}
private fun calculateForeignCurrency(primaryCurrency: Double?, foreignCurrency: Double?):Double?{
    return foreignCurrency?.let { foreignCurrency /(primaryCurrency?:1.0) }
}