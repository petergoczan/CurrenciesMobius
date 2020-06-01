package com.petergoczan.currenciesmobius.presentation

import com.petergoczan.currenciesmobius.mobius.CurrencyEvent
import com.petergoczan.currenciesmobius.mobius.CurrencyModel
import com.petergoczan.currenciesmobius.view.MainPageView
import com.petergoczan.currenciesmobius.view.list.MainPageListRow
import com.spotify.mobius.Connectable
import com.spotify.mobius.MobiusLoop

interface MainPagePresenter : Connectable<CurrencyModel, CurrencyEvent> {

    fun createController(defaultModel: CurrencyModel): MobiusLoop.Controller<CurrencyModel, CurrencyEvent>

    fun onViewAvailable(view: MainPageView)

    fun onModelUpdated(model: CurrencyModel)

    fun onBindViewAtListPosition(position: Int, row: MainPageListRow)

    fun getListItemCount(): Int
}