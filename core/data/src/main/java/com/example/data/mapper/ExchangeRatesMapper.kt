package com.example.data.mapper

import com.example.data.util.CalculateSpread
import com.example.data.util.Constants
import com.example.dto.exchangerates.response.Rates
import com.example.domain.model.exchangerate.RateUiData


fun Rates.toRateUiData(): RateUiData {
    return RateUiData(
        USDtoTRY = TRY.CalculateSpread(2.2f),
        XAGtoTry = calculatePreciousMetals(TRY, XAG).CalculateSpread(2.2f),
        GAUtoTry = calculatePreciousMetals(TRY, XAU).CalculateSpread(2.2f),
        EURtoTRY = calculateForeignCurrency(EUR, TRY).CalculateSpread(2.2f),
        GBPtoTRY = calculateForeignCurrency(GBP, TRY).CalculateSpread(2.2f),
        EURtoUSD = calculateForeignCurrency(EUR, USD).CalculateSpread(2.2f)

    )
}


private fun calculatePreciousMetals(tl: Double?, value: Double?):Double?{
    return value?.let {tl?.div(it) }?.div(Constants.GAU_VALUE)
}
private fun calculateForeignCurrency(primaryCurrency: Double?, foreignCurrency: Double?):Double?{
    return primaryCurrency?.let { primaryCurrency.div(it) }
}