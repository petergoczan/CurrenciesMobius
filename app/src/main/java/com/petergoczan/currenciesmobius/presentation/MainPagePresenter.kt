package com.petergoczan.currenciesmobius.presentation

import com.petergoczan.currenciesmobius.mobius.CurrencyEvent
import com.petergoczan.currenciesmobius.mobius.CurrencyModel
import com.petergoczan.currenciesmobius.view.MainPageView
import com.petergoczan.currenciesmobius.view.list.MainPageListRow
import com.spotify.mobius.Connectable
import com.spotify.mobius.MobiusLoop

interface MainPagePresenter : Connectable<CurrencyModel, CurrencyEvent> {

    fun createController(defaultModel: CurrencyModel)

    fun onResume()

    fun onPause()

    fun onDestroy()

    fun onViewAvailable(view: MainPageView)

    fun onBindViewAtListPosition(position: Int, row: MainPageListRow)

    fun getListItemCount(): Int

    fun initList()

    fun updateList()

    fun getModel(): CurrencyModel

    fun moveItemToTop(originalItemPosition: Int)
}