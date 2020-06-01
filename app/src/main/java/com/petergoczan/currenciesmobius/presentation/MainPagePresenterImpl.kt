package com.petergoczan.currenciesmobius.presentation

import android.util.Log
import com.petergoczan.currenciesmobius.di.ActivityScope
import com.petergoczan.currenciesmobius.mobius.*
import com.petergoczan.currenciesmobius.mobius.effecthandler.CurrencyEffectHandlers
import com.petergoczan.currenciesmobius.view.MainPageView
import com.petergoczan.currenciesmobius.view.list.MainPageListRow
import com.spotify.mobius.Connection
import com.spotify.mobius.MobiusLoop
import com.spotify.mobius.Update
import com.spotify.mobius.android.AndroidLogger
import com.spotify.mobius.android.MobiusAndroid
import com.spotify.mobius.functions.Consumer
import com.spotify.mobius.rx2.RxMobius
import javax.inject.Inject

@ActivityScope
class MainPagePresenterImpl @Inject constructor(
    private val effectHandlers: CurrencyEffectHandlers,
    private val timerEventSource: TimerEventSource
) :
    MainPagePresenter {

    private lateinit var model: CurrencyModel
    private lateinit var view: MainPageView

    override fun onViewAvailable(view: MainPageView) {
        this.view = view
    }

    override fun onModelUpdated(model: CurrencyModel) {
        this.model = model
    }

    override fun onBindViewAtListPosition(position: Int, row: MainPageListRow) {
        //TODO
//        val item: CurrencyItem = model.remoteModel.currencyItems[position]
//        row.setTitle(item.code)
//        row.setSubtitle(item.name)
//        row.setAmount(item.amount)
//        row.setImage(item.imageUrl)
    }

    override fun getListItemCount(): Int {
        //TODO
        return 0//model.remoteModel.currencyItems.size
    }

    override fun connect(output: Consumer<CurrencyEvent>): Connection<CurrencyModel> {
        return object : Connection<CurrencyModel> {
            override fun accept(value: CurrencyModel) {
                Log.d("CurrencyMobius", "accept() called in MainPagePresenter / connect()")
                //TODO render model
            }

            override fun dispose() {
                Log.d("CurrencyMobius", "dispose() called in MainPagePresenter / connect()")
                //TODO dispose of whatever needs to be disposed
            }

        }
    }

    override fun createController(
        defaultModel: CurrencyModel
    ): MobiusLoop.Controller<CurrencyModel, CurrencyEvent> {
        return MobiusAndroid.controller(createLoop(), defaultModel)
    }

    private fun createLoop():
            MobiusLoop.Factory<CurrencyModel, CurrencyEvent, CurrencyEffect> =
        RxMobius.loop(Update<CurrencyModel, CurrencyEvent, CurrencyEffect> { model: CurrencyModel, event: CurrencyEvent ->
            update(
                model,
                event
            )
        }, effectHandlers.createEffectHandlers())
            .eventSource(timerEventSource)
            .logger(AndroidLogger.tag("CurrencyMobius"))
}