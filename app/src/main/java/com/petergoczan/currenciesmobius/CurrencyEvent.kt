package com.petergoczan.currenciesmobius

import com.petergoczan.currenciesmobius.model.CurrencyItem
import com.petergoczan.currenciesmobius.model.RemoteCurrenciesModel

sealed class CurrencyEvent {
    data class InternetStateChanged(val isConnected: Boolean) : CurrencyEvent()
    data class RowSelected(val item: CurrencyItem) : CurrencyEvent()
    data class ReferenceCurrencyAmountChanged(val amount: Int) : CurrencyEvent()
    object RefreshTimePassed : CurrencyEvent()
    data class DataArrived(val data: RemoteCurrenciesModel) : CurrencyEvent()
}
