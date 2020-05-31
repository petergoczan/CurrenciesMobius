package com.petergoczan.currenciesmobius.presentation

import com.petergoczan.currenciesmobius.*
import com.petergoczan.currenciesmobius.di.ActivityScope
import com.petergoczan.currenciesmobius.view.MainPageView
import com.petergoczan.currenciesmobius.view.list.MainPageListRow
import com.spotify.mobius.Connection
import com.spotify.mobius.MobiusLoop
import com.spotify.mobius.Update
import com.spotify.mobius.android.AndroidLogger
import com.spotify.mobius.android.MobiusAndroid
import com.spotify.mobius.functions.Consumer
import com.spotify.mobius.rx2.RxMobius
import io.reactivex.ObservableTransformer
import javax.inject.Inject

@ActivityScope
class MainPagePresenterImpl @Inject constructor() : MainPagePresenter {

    private lateinit var model: CurrencyModel
    private lateinit var view: MainPageView

    override fun onViewAvailable(view: MainPageView) {
        this.view = view
    }

    override fun onModelUpdated(model: CurrencyModel) {
        this.model = model
    }

    override fun onBindViewAtListPosition(position: Int, row: MainPageListRow) {
        val item: CurrencyItem = model.remoteModel.currencyItems[position]
        row.setTitle(item.code)
        row.setSubtitle(item.name)
        row.setAmount(item.amount)
        row.setImage(item.imageUrl)
    }

    override fun getListItemCount(): Int {
        return model.remoteModel.currencyItems.size
    }

    override fun connect(output: Consumer<CurrencyEvent>): Connection<CurrencyModel> {
        return object : Connection<CurrencyModel> {
            override fun accept(value: CurrencyModel) {
                //TODO render model
            }

            override fun dispose() {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

        }
    }

    override fun createController(
        effectHandlers: ObservableTransformer<CurrencyEffect, CurrencyEvent>,
        defaultModel: CurrencyModel
    ): MobiusLoop.Controller<CurrencyModel, CurrencyEvent> {
        return MobiusAndroid.controller(createLoop(effectHandlers), defaultModel)
    }

    private fun createLoop(effectHandlers: ObservableTransformer<CurrencyEffect, CurrencyEvent>): MobiusLoop.Factory<CurrencyModel, CurrencyEvent, CurrencyEffect> {
        return RxMobius.loop(Update<CurrencyModel, CurrencyEvent, CurrencyEffect> { model: CurrencyModel, event: CurrencyEvent ->
            update(
                model,
                event
            )
        }, effectHandlers)
            .logger(AndroidLogger.tag("MainActivity loop"))
    }
}