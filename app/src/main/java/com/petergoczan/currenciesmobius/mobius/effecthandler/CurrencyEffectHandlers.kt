package com.petergoczan.currenciesmobius.mobius.effecthandler

import com.petergoczan.currenciesmobius.di.ActivityScope
import com.petergoczan.currenciesmobius.mobius.CurrencyEffect
import com.petergoczan.currenciesmobius.mobius.CurrencyEffect.*
import com.petergoczan.currenciesmobius.mobius.CurrencyEvent
import com.petergoczan.currenciesmobius.presentation.MainPagePresenter
import com.petergoczan.currenciesmobius.scheduler.SchedulersProvider
import com.spotify.mobius.rx2.RxMobius
import io.reactivex.ObservableTransformer
import javax.inject.Inject

@ActivityScope
class CurrencyEffectHandlers @Inject constructor(
    private val requestDataEffectHandler: RequestDataEffectHandler,
    private val schedulersProvider: SchedulersProvider
) {

    fun createEffectHandlers(presenter: MainPagePresenter): ObservableTransformer<CurrencyEffect, CurrencyEvent> =
        RxMobius
            .subtypeEffectHandler<CurrencyEffect, CurrencyEvent>()
            .addTransformer<RequestData>(
                RequestData::class.java,
                requestDataEffectHandler
            )
            .addConsumer<InitListItems>(
                InitListItems::class.java,
                { presenter.initList() },
                schedulersProvider.ui()
            )
            .addConsumer<UpdateListItems>(
                UpdateListItems::class.java,
                { presenter.updateList() },
                schedulersProvider.ui()
            )
            .addConsumer<MoveItemOnTop>(
                MoveItemOnTop::class.java,
                { presenter.moveItemToTop(it.itemPosition) },
                schedulersProvider.ui()
            )
            .addConsumer<HandleConnectionStateChanged>(
                HandleConnectionStateChanged::class.java,
                { presenter.handleConnectionStateChanged() },
                schedulersProvider.ui()
            )
            .addConsumer<HandleCommunicationError>(
                HandleCommunicationError::class.java,
                { presenter.handleCommunicationError() },
                schedulersProvider.ui()
            )
            .build()
}