package com.petergoczan.currenciesmobius

import com.petergoczan.currenciesmobius.mobius.*
import com.petergoczan.currenciesmobius.mobius.CurrencyEffect.*
import com.petergoczan.currenciesmobius.mobius.CurrencyEvent.*
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
        val model =
            CurrencyModel(isOnline = false)
        updateSpec
            .given(model)
            .whenEvent(InternetStateChanged(true))
            .then(assertThatNext(hasModel(model.copy(isOnline = true))))
    }

    @Test
    fun updateOnline_whenInternetStateChangedToUnavailable() {
        val model =
            CurrencyModel(isOnline = true)
        updateSpec
            .given(model)
            .whenEvent(InternetStateChanged(false))
            .then(assertThatNext(hasModel(model.copy(isOnline = false))))
    }

    @Test
    fun showNoInternetPage_whenInternetStateChangedToUnavailable() {
        val model =
            CurrencyModel(isOnline = true)
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
        val model =
            CurrencyModel(isOnline = false)
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
        val selectedItemCode = "TEST"
        updateSpec
            .given(model)
            .whenEvent(RowSelected(selectedItemCode))
            .then(assertThatNext(hasModel(model.copy(selectedItemCode = selectedItemCode))))
    }

    @Test
    fun moveItemOnTop_whenRowSelected() {
        val selectedItemCode = "TEST"
        updateSpec
            .given(CurrencyModel())
            .whenEvent(RowSelected(selectedItemCode))
            .then(
                assertThatNext<CurrencyModel, CurrencyEffect>(
                    hasEffects(MoveItemOnTop(selectedItemCode))
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
        val selectedItemCode = "TEST"
        val model = CurrencyModel(selectedItemCode = selectedItemCode)
        updateSpec
            .given(model)
            .whenEvent(RefreshTimePassed)
            .then(
                assertThatNext<CurrencyModel, CurrencyEffect>(
                    hasEffects(RequestData(selectedItemCode))
                )
            )
    }

    @Test
    fun updateRemoteModel_whenDataArrived() {
        val originalModel = listOf<CurrencyListItem>()
        val newModel = listOf<CurrencyListItem>()
        val model = CurrencyModel(items = originalModel)
        updateSpec
            .given(model)
            .whenEvent(DataArrived(newModel))
            .then(assertThatNext(hasModel(model.copy(items = newModel))))
    }

    @Test
    fun updateListItems_whenDataArrived() {
        val newModel = listOf<CurrencyListItem>()
        updateSpec
            .given(CurrencyModel())
            .whenEvent(DataArrived(newModel))
            .then(
                assertThatNext<CurrencyModel, CurrencyEffect>(
                    hasEffects(UpdateListItems)
                )
            )
    }

    @Test
    fun showErrorPage_whenCommunicationError() {
        val model = CurrencyModel()
        updateSpec
            .given(model)
            .whenEvent(CommunicationError)
            .then(
                assertThatNext<CurrencyModel, CurrencyEffect>(
                    hasEffects(ShowCommunicationErrorPage)
                )
            )
    }
}