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
            model.copy(amountSetByUser = event.amount)
        )
        is RefreshTimePassed -> dispatch(
            setOf(RequestData(model.selectedItemCode))
        )
        is DataArrived -> next(
            model.copy(items = event.items)
//            setOf(UpdateListItems)
        )
        is CommunicationError -> dispatch(setOf(ShowCommunicationErrorPage))
    }

data class CurrencyModel(
    val isOnline: Boolean = true,
    val selectedItemCode: String = DEFAULT_CURRENCY_CODE,
    val amountSetByUser: Int = 0,
    val items: List<CurrencyListItem> = listOf()
)

data class RemoteCurrenciesModel(
    val baseCurrency: String = DEFAULT_CURRENCY_CODE,
    val rates: RemoteCurrencyRates = RemoteCurrencyRates()
)

data class RemoteCurrencyRates(
    val AUD: Double = 0.0,
    val BGN: Double = 0.0,
    val BRL: Double = 0.0,
    val CAD: Double = 0.0,
    val CHF: Double = 0.0,
    val CNY: Double = 0.0,
    val CZK: Double = 0.0,
    val DKK: Double = 0.0,
    val EUR: Double = 0.0,
    val GBP: Double = 0.0,
    val HKD: Double = 0.0,
    val HRK: Double = 0.0,
    val IDR: Double = 0.0,
    val ILS: Double = 0.0,
    val INR: Double = 0.0,
    val ISK: Double = 0.0,
    val JPY: Double = 0.0,
    val KRW: Double = 0.0,
    val MXN: Double = 0.0,
    val MYR: Double = 0.0,
    val NOK: Double = 0.0,
    val NZD: Double = 0.0,
    val PHP: Double = 0.0,
    val PLN: Double = 0.0,
    val RON: Double = 0.0,
    val RUB: Double = 0.0,
    val SEK: Double = 0.0,
    val SGD: Double = 0.0,
    val THB: Double = 0.0,
    val USD: Double = 0.0,
    val ZAR: Double = 0.0
)

@Parcelize
data class CurrencyListItem(
    val imageUrl: String = "",
    val code: String = "",
    val name: String = "",
    val amount: Float = 0.0F,
    val multiplierForBaseCurrency: Double = 0.0
) : Parcelable

sealed class CurrencyEvent {
    data class InternetStateChanged(val isConnected: Boolean) : CurrencyEvent()
    data class RowSelected(val selectedItemCode: String) : CurrencyEvent()
    data class ReferenceCurrencyAmountChanged(val amount: Int) : CurrencyEvent()
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