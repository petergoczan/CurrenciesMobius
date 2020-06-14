package com.petergoczan.currenciesmobius.mobius

import android.os.Parcelable
import com.petergoczan.currenciesmobius.data.getReorderedItemsByExistingList
import com.petergoczan.currenciesmobius.data.getReorderedItemsForItemClick
import com.petergoczan.currenciesmobius.mobius.CurrencyEffect.*
import com.petergoczan.currenciesmobius.mobius.CurrencyEvent.*
import com.spotify.mobius.Next
import com.spotify.mobius.Next.*
import kotlinx.android.parcel.Parcelize

fun update(
    model: CurrencyModel,
    event: CurrencyEvent
): Next<CurrencyModel, CurrencyEffect> =
    when (event) {
        is InternetStateChanged -> next(
            model.copy(isOnline = event.isConnected),
            setOf(HandleConnectionStateChanged)
        )
        is RowSelected -> {
            val reorderedItems = getReorderedItemsForItemClick(model.items, event.itemPosition)
            next(
                model.copy(
                    items = reorderedItems,
                    amountSetByUser = model.amountSetByUser * reorderedItems[0].multiplierForBaseCurrency,
                    baseCurrencyCode = reorderedItems[0].code
                ),
                setOf(MoveItemOnTop(event.itemPosition))
            )
        }
        is ReferenceCurrencyAmountChanged ->
            next(
                model.copy(amountSetByUser = event.amount),
                setOf(UpdateListItems)
            )
        is RefreshTimePassed -> {
            if (model.isOnline) {
                next(model, setOf(RequestData(model.baseCurrencyCode) as CurrencyEffect))
            } else {
                noChange()
            }
        }
        is DataArrived -> {
            val effect = if (model.items.isEmpty()) {
                InitListItems
            } else {
                UpdateListItems
            }
            next(
                model.copy(
                    items = if (model.items.isNotEmpty()) {
                        getReorderedItemsByExistingList(model, event.items)
                    } else {
                        event.items
                    }
                ),
                setOf(effect)
            )
        }
        is CommunicationError -> dispatch(setOf(ShowCommunicationErrorPage))
    }

data class CurrencyModel(
    val baseCurrencyCode: String = DEFAULT_CURRENCY_CODE,
    val isOnline: Boolean = false,
    val amountSetByUser: Double = DEFAULT_AMOUNT,
    val items: List<CurrencyListItem> = listOf()
)

@Parcelize
data class CurrencyListItem(
    val imageUrl: String = "",
    val code: String = "",
    val name: String = "",
    val multiplierForBaseCurrency: Float = 0F
) : Parcelable

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

sealed class CurrencyEvent {
    data class InternetStateChanged(val isConnected: Boolean) : CurrencyEvent()
    data class RowSelected(val itemPosition: Int) : CurrencyEvent()
    data class ReferenceCurrencyAmountChanged(val amount: Double) : CurrencyEvent()
    object RefreshTimePassed : CurrencyEvent()
    data class DataArrived(val items: List<CurrencyListItem>) : CurrencyEvent()
    object CommunicationError : CurrencyEvent()
}

sealed class CurrencyEffect {
    data class RequestData(val baseCurrencyCode: String) : CurrencyEffect()
    object InitListItems : CurrencyEffect()
    object UpdateListItems : CurrencyEffect()
    object ShowCommunicationErrorPage : CurrencyEffect()
    data class MoveItemOnTop(val itemPosition: Int) : CurrencyEffect()
    object HandleConnectionStateChanged : CurrencyEffect()
}

const val DEFAULT_CURRENCY_CODE = "EUR"
const val DEFAULT_AMOUNT = 100.0