package com.example.data.util


fun Double?.calculateSpread(spread: Float): Pair<Double, Double> =
    Pair(this?.minus(spread) ?: 0.0, this?.plus(spread) ?: 0.0)




