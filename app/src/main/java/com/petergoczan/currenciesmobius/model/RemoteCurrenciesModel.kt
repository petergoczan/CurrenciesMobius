package com.petergoczan.currenciesmobius.model

data class RemoteCurrenciesModel(val baseCurrency: String,
                                 val currencyItems: List<CurrencyItem>)

data class CurrencyItem(val code: String, val multiplierForBaseCurrency: Double)