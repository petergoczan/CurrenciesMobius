package com.petergoczan.currenciesmobius.presentation

import com.petergoczan.currenciesmobius.CurrencyEffect
import com.petergoczan.currenciesmobius.CurrencyEvent
import com.petergoczan.currenciesmobius.CurrencyModel
import com.petergoczan.currenciesmobius.view.list.MainPageListRow
import com.petergoczan.currenciesmobius.view.MainPageView
import com.spotify.mobius.Connectable
import com.spotify.mobius.MobiusLoop
import io.reactivex.ObservableTransformer

interface MainPagePresenter : Connectable<CurrencyModel, CurrencyEvent> {

    fun createController(
        effectHandlers: ObservableTransformer<CurrencyEffect, CurrencyEvent>,
        defaultModel: CurrencyModel
    ): MobiusLoop.Controller<CurrencyModel, CurrencyEvent>

    fun onViewAvailable(view: MainPageView)

    fun onModelUpdated(model: CurrencyModel)

    fun onBindViewAtListPosition(position: Int, row: MainPageListRow)

    fun getListItemCount(): Int
}