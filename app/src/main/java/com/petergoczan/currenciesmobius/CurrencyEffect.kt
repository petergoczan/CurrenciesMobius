package com.petergoczan.currenciesmobius

import com.petergoczan.currenciesmobius.model.CurrencyItem

sealed class CurrencyEffect {
    data class RequestData(val baseCurrencyCode: String) : CurrencyEffect()
    object UpdateListItems : CurrencyEffect()
    data class MoveItemOnTop(val itemToMove: CurrencyItem) : CurrencyEffect()
    object ShowNoInternetPage : CurrencyEffect()
}
