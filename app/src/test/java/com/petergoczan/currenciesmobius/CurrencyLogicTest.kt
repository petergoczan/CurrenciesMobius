package com.petergoczan.currenciesmobius

import com.petergoczan.currenciesmobius.CurrencyEffect.*
import com.petergoczan.currenciesmobius.CurrencyEvent.*
import com.spotify.mobius.test.NextMatchers.hasEffects
import com.spotify.mobius.test.NextMatchers.hasModel
import com.spotify.mobius.test.UpdateSpec
import com.spotify.mobius.test.UpdateSpec.assertThatNext
import org.junit.Before
import org.junit.Test

class CurrencyLogicTest {

    private lateinit var updateSpec: UpdateSpec<CurrencyModel, CurrencyEvent, CurrencyEffect>

    @Before
    fun setUp() {
        updateSpec = UpdateSpec(::update)
    }

    @Test
    fun updateOnline_whenInternetStateChangedToAvailable() {
        val model = CurrencyModel(isOnline = false)
        updateSpec
            .given(model)
            .whenEvent(InternetStateChanged(true))
            .then(assertThatNext(hasModel(model.copy(isOnline = true))))
    }

    @Test
    fun updateOnline_whenInternetStateChangedToUnavailable() {
        val model = CurrencyModel(isOnline = true)
        updateSpec
            .given(model)
            .whenEvent(InternetStateChanged(false))
            .then(assertThatNext(hasModel(model.copy(isOnline = false))))
    }

    @Test
    fun showNoInternetPage_whenInternetStateChangedToUnavailable() {
        val model = CurrencyModel(isOnline = true)
        updateSpec
            .given(model)
            .whenEvent(InternetStateChanged(false))
            .then(
                assertThatNext<CurrencyModel, CurrencyEffect>(
                    hasEffects(ShowNoInternetPage)
                )
            )
    }

    @Test
    fun hideNoInternetPage_whenInternetStateChangedToAvailable() {
        val model = CurrencyModel(isOnline = false)
        updateSpec
            .given(model)
            .whenEvent(InternetStateChanged(true))
            .then(
                assertThatNext<CurrencyModel, CurrencyEffect>(
                    hasEffects(HideNoInternetPage)
                )
            )
    }

    @Test
    fun updateSelectedItem_whenRowSelected() {
        val model = CurrencyModel()
        val selectedItem = CurrencyItem("test", 27.toDouble())
        updateSpec
            .given(model)
            .whenEvent(RowSelected(selectedItem))
            .then(assertThatNext(hasModel(model.copy(selectedItem = selectedItem))))
    }

    @Test
    fun moveItemOnTop_whenRowSelected() {
        val selectedItem = CurrencyItem()
        updateSpec
            .given(CurrencyModel())
            .whenEvent(RowSelected(selectedItem))
            .then(
                assertThatNext<CurrencyModel, CurrencyEffect>(
                    hasEffects(MoveItemOnTop(selectedItem))
                )
            )
    }

    @Test
    fun updateAmount_whenReferenceCurrencyAmountChanged() {
        val model = CurrencyModel()
        updateSpec
            .given(model)
            .whenEvent(ReferenceCurrencyAmountChanged(123))
            .then(assertThatNext(hasModel(model.copy(amountSetByUser = 123))))
    }

    @Test
    fun requestDataForDefaultCurrency_whenRefreshTimePassed_AndNothingIsSelectedYet() {
        updateSpec
            .given(CurrencyModel())
            .whenEvent(RefreshTimePassed)
            .then(
                assertThatNext<CurrencyModel, CurrencyEffect>(
                    hasEffects(RequestData(DEFAULT_CURRENCY_CODE))
                )
            )
    }

    @Test
    fun requestDataWithCurrentSelection_whenRefreshTimePassed() {
        val selectedItem = CurrencyItem("test", 27.toDouble())
        val model = CurrencyModel(selectedItem = selectedItem)
        updateSpec
            .given(model)
            .whenEvent(RefreshTimePassed)
            .then(
                assertThatNext<CurrencyModel, CurrencyEffect>(
                    hasEffects(RequestData(selectedItem.code))
                )
            )
    }

    @Test
    fun updateRemoteModel_whenDataArrived() {
        val remoteModelOriginal = RemoteCurrenciesModel()
        val remoteModelNew = RemoteCurrenciesModel()
        val model = CurrencyModel(remoteModel = remoteModelOriginal)
        updateSpec
            .given(model)
            .whenEvent(DataArrived(remoteModelNew))
            .then(assertThatNext(hasModel(model.copy(remoteModel = remoteModelNew))))
    }

    @Test
    fun updateListItems_whenDataArrived() {
        val model = CurrencyModel()
        updateSpec
            .given(model)
            .whenEvent(DataArrived(RemoteCurrenciesModel()))
            .then(
                assertThatNext<CurrencyModel, CurrencyEffect>(
                    hasEffects(UpdateListItems)
                )
            )
    }
}