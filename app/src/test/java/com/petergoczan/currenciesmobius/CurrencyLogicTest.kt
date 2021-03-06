package com.petergoczan.currenciesmobius

import com.petergoczan.currenciesmobius.mobius.*
import com.petergoczan.currenciesmobius.mobius.CurrencyEffect.*
import com.petergoczan.currenciesmobius.mobius.CurrencyEvent.*
import com.spotify.mobius.test.NextMatchers.*
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
    fun updateOnline_whenInternetStateChangedToAvailable_andTriggerConnectionChangedEffect() {
        val model = CurrencyModel(isOnline = false)
        updateSpec
            .given(model)
            .whenEvent(InternetStateChanged(true))
            .then(
                assertThatNext<CurrencyModel, CurrencyEffect>(
                    hasModel(model.copy(isOnline = true)),
                    hasEffects(HandleConnectionStateChanged)
                )
            )
    }

    @Test
    fun updateOnline_whenInternetStateChangedToUnavailable_andTriggerConnectionChangedEffect() {
        val model =
            CurrencyModel(isOnline = true)
        updateSpec
            .given(model)
            .whenEvent(InternetStateChanged(false))
            .then(
                assertThatNext<CurrencyModel, CurrencyEffect>(
                    hasModel(model.copy(isOnline = false)),
                    hasEffects(HandleConnectionStateChanged)
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
                    hasEffects(HandleConnectionStateChanged)
                )
            )
    }

    @Test
    fun updateModel_whenRowSelected() {
        val selectedItemPosition = 2
        val firstItem = CurrencyListItem(code = "TEST1", multiplierForBaseCurrency = 2f)
        val secondItem = CurrencyListItem(code = "TEST2", multiplierForBaseCurrency = 3f)
        val thirdItem = CurrencyListItem(code = "TEST3", multiplierForBaseCurrency = 4f)
        val itemsBeforeUpdate = listOf(firstItem, secondItem, thirdItem)
        val itemsAfterUpdate = listOf(thirdItem, firstItem, secondItem)
        val model = CurrencyModel(
            items = itemsBeforeUpdate,
            amountSetByUser = 2.0,
            dataOutdatedFromItemClicked = false
        )
        updateSpec
            .given(model)
            .whenEvent(RowSelected(selectedItemPosition))
            .then(
                assertThatNext<CurrencyModel, CurrencyEffect>(
                    hasModel(
                        model.copy(
                            items = itemsAfterUpdate,
                            baseCurrencyCode = "TEST3",
                            amountSetByUser = 8.0,
                            dataOutdatedFromItemClicked = true
                        )
                    ),
                    hasEffects(MoveItemOnTop(selectedItemPosition))

                )
            )
    }

    @Test
    fun doNotDoAnything_whenRowSelected_andDataOutdated() {
        val selectedItemPosition = 1
        val firstItem = CurrencyListItem(code = "TEST1", multiplierForBaseCurrency = 2f)
        val secondItem = CurrencyListItem(code = "TEST2", multiplierForBaseCurrency = 3f)
        val items = listOf(firstItem, secondItem)
        val model =
            CurrencyModel(items = items, amountSetByUser = 2.0, dataOutdatedFromItemClicked = true)
        updateSpec
            .given(model)
            .whenEvent(RowSelected(selectedItemPosition))
            .then(
                assertThatNext<CurrencyModel, CurrencyEffect>(
                    hasNoModel(),
                    hasNoEffects()
                )
            )
    }

    @Test
    fun updateAmount_whenReferenceCurrencyAmountChanged() {
        val model = CurrencyModel()
        val amount = 123.0
        updateSpec
            .given(model)
            .whenEvent(ReferenceCurrencyAmountChanged(amount))
            .then(
                assertThatNext<CurrencyModel, CurrencyEffect>(
                    hasModel(model.copy(amountSetByUser = amount)),
                    hasEffects(UpdateListItems)
                )
            )
    }

    @Test
    fun requestDataForDefaultCurrency_whenRefreshTimePassed_andNothingIsSelectedYet_andNotOnline() {
        updateSpec
            .given(CurrencyModel(isOnline = true))
            .whenEvent(RefreshTimePassed)
            .then(
                assertThatNext<CurrencyModel, CurrencyEffect>(
                    hasEffects(RequestData(DEFAULT_CURRENCY_CODE))
                )
            )
    }

    @Test
    fun requestDataWithCurrentSelection_whenRefreshTimePassed_andOnline() {
        val selectedItemCode = "TEST1"
        val model = CurrencyModel(
            baseCurrencyCode = selectedItemCode,
            isOnline = true
        )
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
    fun doNotToAnything_whenRefreshTimePassed_andNotOnline() {
        val model = CurrencyModel(isOnline = false)
        updateSpec
            .given(model)
            .whenEvent(RefreshTimePassed)
            .then(
                assertThatNext<CurrencyModel, CurrencyEffect>(
                    hasNoModel(),
                    hasNoEffects()
                )
            )
    }

    @Test
    fun updateRemoteModel_whenDataArrived_AndModelIsEmpty() {
        val firstItemAfterUpdate = CurrencyListItem(code = "TEST1", multiplierForBaseCurrency = 5f)
        val secondItemAfterUpdate = CurrencyListItem(code = "TEST2", multiplierForBaseCurrency = 6f)
        val thirdItemAfterUpdate = CurrencyListItem(code = "TEST3", multiplierForBaseCurrency = 7f)
        val newItems = listOf(firstItemAfterUpdate, secondItemAfterUpdate, thirdItemAfterUpdate)
        val model = CurrencyModel()
        updateSpec
            .given(model)
            .whenEvent(DataArrived(newItems))
            .then(
                assertThatNext(
                    hasModel(
                        model.copy(
                            items = newItems,
                            dataOutdatedFromItemClicked = false
                        )
                    )
                )
            )
    }

    @Test
    fun updateRemoteModel_whenDataArrived_AndModelIsNotEmpty() {
        val firstItemBeforeUpdate = CurrencyListItem(code = "TEST1", multiplierForBaseCurrency = 2f)
        val secondItemBeforeUpdate =
            CurrencyListItem(code = "TEST2", multiplierForBaseCurrency = 3f)
        val thirdItemBeforeUpdate = CurrencyListItem(code = "TEST3", multiplierForBaseCurrency = 4f)
        val firstItemAfterUpdate = CurrencyListItem(code = "TEST1", multiplierForBaseCurrency = 5f)
        val secondItemAfterUpdate = CurrencyListItem(code = "TEST2", multiplierForBaseCurrency = 6f)
        val thirdItemAfterUpdate = CurrencyListItem(code = "TEST3", multiplierForBaseCurrency = 7f)
        val itemsBeforeUpdate =
            listOf(thirdItemBeforeUpdate, firstItemBeforeUpdate, secondItemBeforeUpdate)
        val newItems = listOf(firstItemAfterUpdate, secondItemAfterUpdate, thirdItemAfterUpdate)
        val newItemsAfterReordering =
            listOf(thirdItemAfterUpdate, firstItemAfterUpdate, secondItemAfterUpdate)
        val model = CurrencyModel(items = itemsBeforeUpdate, dataOutdatedFromItemClicked = false)
        updateSpec
            .given(model)
            .whenEvent(DataArrived(newItems))
            .then(assertThatNext(hasModel(model.copy(items = newItemsAfterReordering))))
    }

    @Test
    fun initListItems_whenDataArrived_AndModelIsEmpty() {
        updateSpec
            .given(CurrencyModel())
            .whenEvent(DataArrived(listOf()))
            .then(
                assertThatNext<CurrencyModel, CurrencyEffect>(
                    hasEffects(InitListItems)
                )
            )
    }

    @Test
    fun updateListItems_whenDataArrived_AndModelIsNotEmpty() {
        updateSpec
            .given(CurrencyModel(items = listOf(CurrencyListItem())))
            .whenEvent(DataArrived(listOf(CurrencyListItem())))
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
                    hasEffects(HandleCommunicationError)
                )
            )
    }
}