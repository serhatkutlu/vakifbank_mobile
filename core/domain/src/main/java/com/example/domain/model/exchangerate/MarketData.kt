package com.example.domain.model.exchangerate

data class MarketData(
    val name: String,
    val category: ExchangeRateCategory,
    val buy: Double,
    val sell: Double
)
