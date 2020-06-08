package com.petergoczan.currenciesmobius.mobius

import android.os.Parcelable
import com.petergoczan.currenciesmobius.mobius.CurrencyEffect.*
import com.petergoczan.currenciesmobius.mobius.CurrencyEvent.*
import com.spotify.mobius.Next
import com.spotify.mobius.Next.dispatch
import com.spotify.mobius.Next.next
import kotlinx.android.parcel.Parcelize

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
            model.copy(selectedItemCode = event.selectedItemCode),
            setOf(MoveItemOnTop(event.selectedItemCode))
        )
        is ReferenceCurrencyAmountChanged -> next(
            model.copy(amountSetByUser = event.amount),
            setOf(UpdateListItems)
        )
        is RefreshTimePassed -> dispatch(
            setOf(RequestData(model.selectedItemCode))
        )
        is DataArrived -> next(
            model.copy(items = event.items),
            setOf(UpdateListItems)
        )
        is CommunicationError -> dispatch(setOf(ShowCommunicationErrorPage))
    }

data class CurrencyModel(
    val isOnline: Boolean = true,
    val selectedItemCode: String = DEFAULT_CURRENCY_CODE,
    val amountSetByUser: Float = 0f,
    val items: List<CurrencyListItem> = listOf()
)

data class RemoteCurrenciesModel(
    val baseCurrency: String = DEFAULT_CURRENCY_CODE,
    val rates: RemoteCurrencyRates = RemoteCurrencyRates()
)

data class RemoteCurrencyRates(
    val AUD: Float = 0F,
    val BGN: Float = 0F,
    val BRL: Float = 0F,
    val CAD: Float = 0F,
    val CHF: Float = 0F,
    val CNY: Float = 0F,
    val CZK: Float = 0F,
    val DKK: Float = 0F,
    val EUR: Float = 0F,
    val GBP: Float = 0F,
    val HKD: Float = 0F,
    val HRK: Float = 0F,
    val IDR: Float = 0F,
    val ILS: Float = 0F,
    val INR: Float = 0F,
    val ISK: Float = 0F,
    val JPY: Float = 0F,
    val KRW: Float = 0F,
    val MXN: Float = 0F,
    val MYR: Float = 0F,
    val NOK: Float = 0F,
    val NZD: Float = 0F,
    val PHP: Float = 0F,
    val PLN: Float = 0F,
    val RON: Float = 0F,
    val RUB: Float = 0F,
    val SEK: Float = 0F,
    val SGD: Float = 0F,
    val THB: Float = 0F,
    val USD: Float = 0F,
    val ZAR: Float = 0F
)

@Parcelize
data class CurrencyListItem(
    val imageUrl: String = "",
    val code: String = "",
    val name: String = "",
    val amount: Float = 0F,
    val multiplierForBaseCurrency: Float = 0F
) : Parcelable

sealed class CurrencyEvent {
    data class InternetStateChanged(val isConnected: Boolean) : CurrencyEvent()
    data class RowSelected(val selectedItemCode: String) : CurrencyEvent()
    data class ReferenceCurrencyAmountChanged(val amount: Float) : CurrencyEvent()
    object RefreshTimePassed : CurrencyEvent()
    data class DataArrived(val items: List<CurrencyListItem>) : CurrencyEvent()
    object CommunicationError : CurrencyEvent()
}

sealed class CurrencyEffect {
    data class RequestData(val baseCurrencyCode: String) : CurrencyEffect()
    object UpdateListItems : CurrencyEffect()
    object ShowCommunicationErrorPage : CurrencyEffect()
    data class MoveItemOnTop(val codeOfItemToMove: String) : CurrencyEffect()
    object ShowNoInternetPage : CurrencyEffect()
    object HideNoInternetPage : CurrencyEffect()
}

const val DEFAULT_CURRENCY_CODE = "EUR"