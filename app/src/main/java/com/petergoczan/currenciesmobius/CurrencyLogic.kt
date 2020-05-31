package com.petergoczan.currenciesmobius

import com.petergoczan.currenciesmobius.CurrencyEffect.*
import com.petergoczan.currenciesmobius.CurrencyEvent.*
import com.spotify.mobius.Next
import com.spotify.mobius.Next.dispatch
import com.spotify.mobius.Next.next
import java.io.Serializable

data class CurrencyModel(
    val isOnline: Boolean = true,
    val selectedItem: CurrencyItem? = null,
    val amountSetByUser: Int = 0,
    val remoteModel: RemoteCurrenciesModel = RemoteCurrenciesModel()
)

data class RemoteCurrenciesModel(
    val baseCurrency: String = DEFAULT_CURRENCY_CODE,
    val currencyItems: List<CurrencyItem> = listOf()
) : Serializable

data class CurrencyItem(
    val imageUrl: String = "",
    val code: String = "",
    val name: String = "",
    val amount: Float = 0.0F,
    val multiplierForBaseCurrency: Double = 0.0
) : Serializable

sealed class CurrencyEvent {
    data class InternetStateChanged(val isConnected: Boolean) : CurrencyEvent()
    data class RowSelected(val selectedItem: CurrencyItem) : CurrencyEvent()
    data class ReferenceCurrencyAmountChanged(val amount: Int) : CurrencyEvent()
    object RefreshTimePassed : CurrencyEvent()
    data class DataArrived(val data: RemoteCurrenciesModel) : CurrencyEvent()
}

sealed class CurrencyEffect {
    data class RequestData(val baseCurrencyCode: String) : CurrencyEffect()
    object UpdateListItems : CurrencyEffect()
    data class MoveItemOnTop(val itemToMove: CurrencyItem) : CurrencyEffect()
    object ShowNoInternetPage : CurrencyEffect()
    object HideNoInternetPage : CurrencyEffect()
}

const val DEFAULT_CURRENCY_CODE = "EUR"

fun update(
    model: CurrencyModel,
    event: CurrencyEvent
): Next<CurrencyModel, CurrencyEffect> =
    when (event) {
        is InternetStateChanged -> next(
            model.copy(isOnline = event.isConnected),
            setOf(if (event.isConnected) HideNoInternetPage else ShowNoInternetPage)
        )
        is RowSelected -> next(
            model.copy(selectedItem = event.selectedItem),
            setOf(MoveItemOnTop(event.selectedItem))
        )
        is ReferenceCurrencyAmountChanged -> next(
            model.copy(amountSetByUser = event.amount)
        )
        is RefreshTimePassed -> dispatch(
            setOf(
                RequestData(
                    model.selectedItem?.code ?: DEFAULT_CURRENCY_CODE
                )
            )
        )
        is DataArrived -> next(
            model.copy(remoteModel = event.data),
            setOf(UpdateListItems)
        )
    }