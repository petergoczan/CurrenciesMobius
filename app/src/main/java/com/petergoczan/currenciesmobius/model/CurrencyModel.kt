package com.petergoczan.currenciesmobius.model

data class CurrencyModel(
    val isOnline: Boolean,
    val selectedItem: CurrencyItem,
    val amountSetByUser: Int,
    val remoteModel: RemoteCurrenciesModel
)
