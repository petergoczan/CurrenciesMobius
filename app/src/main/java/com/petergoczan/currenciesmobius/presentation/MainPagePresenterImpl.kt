package com.petergoczan.currenciesmobius.presentation

import android.util.Log
import com.petergoczan.currenciesmobius.di.ActivityScope
import com.petergoczan.currenciesmobius.mobius.*
import com.petergoczan.currenciesmobius.mobius.effecthandler.CurrencyEffectHandlers
import com.petergoczan.currenciesmobius.mobius.eventsource.InternetConnectionEventSource
import com.petergoczan.currenciesmobius.mobius.eventsource.TimerEventSource
import com.petergoczan.currenciesmobius.roundToTwoDecimals
import com.petergoczan.currenciesmobius.view.MainPageView
import com.petergoczan.currenciesmobius.view.list.MainPageListRow
import com.spotify.mobius.Connection
import com.spotify.mobius.MobiusLoop
import com.spotify.mobius.Update
import com.spotify.mobius.android.AndroidLogger
import com.spotify.mobius.android.MobiusAndroid
import com.spotify.mobius.functions.Consumer
import com.spotify.mobius.rx2.RxMobius
import com.squareup.picasso.Picasso
import javax.inject.Inject


@ActivityScope
class MainPagePresenterImpl @Inject constructor(
    private val effectHandlers: CurrencyEffectHandlers,
    private val timerEventSource: TimerEventSource,
    private val internetConnectionEventSource: InternetConnectionEventSource,
    private val picasso: Picasso
) : MainPagePresenter {

    private lateinit var controller: MobiusLoop.Controller<CurrencyModel, CurrencyEvent>
    private lateinit var view: MainPageView
    private lateinit var eventConsumer: Consumer<CurrencyEvent>

    override fun onViewAvailable(view: MainPageView) {
        this.view = view
    }

    override fun createController(defaultModel: CurrencyModel) {
        controller = MobiusAndroid.controller(createLoop(), defaultModel)
        controller.connect(this)
    }

    override fun onResume() {
        controller.start()
    }

    override fun onPause() {
        controller.stop()
    }

    override fun onDestroy() {
        controller.disconnect()
    }

    override fun getModel(): CurrencyModel {
        return controller.model
    }

    override fun moveItemToTop(originalItemPosition: Int) {
        view.notifyItemMovedToTop(originalItemPosition)
    }

    override fun onBindViewAtListPosition(position: Int, row: MainPageListRow) {
        val item: CurrencyListItem = getModel().items[position]
        row.setTitle(item.code)
        row.setSubtitle(item.name)
        row.setImage(picasso, item.imageUrl)
        row.setRowSelectedListener { eventConsumer.accept(CurrencyEvent.RowSelected(it)) }
        setupRowAmount(position, row, item.multiplierForBaseCurrency)
    }

    override fun getListItemCount(): Int {
        return controller.model.items.size
    }

    override fun initList() {
        view.initList()
    }

    override fun updateList() {
        view.updateList()
    }

    override fun handleConnectionStateChanged() {
        if (getModel().isOnline) {
            view.hideNoInternetConnectionPage()
        } else {
            view.showNoInternetConnectionPage()
        }
    }

    override fun connect(output: Consumer<CurrencyEvent>): Connection<CurrencyModel> {

        eventConsumer = output

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

    private fun setupRowAmount(position: Int, row: MainPageListRow, itemMultiplier: Float) {
        row.setAmount(
            if (position == 0) {
                getModel().amountSetByUser
            } else {
                (getModel().amountSetByUser * itemMultiplier).roundToTwoDecimals()
            }
        )
        row.setupAmountChangedListener {
            eventConsumer.accept(
                CurrencyEvent.ReferenceCurrencyAmountChanged(amount = it)
            )
        }
    }

    private fun createLoop():
            MobiusLoop.Factory<CurrencyModel, CurrencyEvent, CurrencyEffect> =
        RxMobius.loop(Update<CurrencyModel, CurrencyEvent, CurrencyEffect> { model: CurrencyModel, event: CurrencyEvent ->
            update(
                model,
                event
            )
        }, effectHandlers.createEffectHandlers(this))
            .eventSources(timerEventSource, internetConnectionEventSource)
            .logger(AndroidLogger.tag("CurrencyMobius"))
}