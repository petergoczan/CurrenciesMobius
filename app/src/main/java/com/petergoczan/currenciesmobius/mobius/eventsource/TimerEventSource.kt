package com.petergoczan.currenciesmobius.mobius.eventsource

import com.petergoczan.currenciesmobius.di.ActivityScope
import com.petergoczan.currenciesmobius.mobius.CurrencyEvent
import com.petergoczan.currenciesmobius.mobius.CurrencyEvent.RefreshTimePassed
import com.petergoczan.currenciesmobius.scheduler.SchedulersProvider
import com.spotify.mobius.EventSource
import com.spotify.mobius.disposables.Disposable
import com.spotify.mobius.functions.Consumer
import io.reactivex.Observable
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@ActivityScope
class TimerEventSource @Inject constructor(private val schedulersProvider: SchedulersProvider) :
    EventSource<CurrencyEvent> {

    override fun subscribe(eventConsumer: Consumer<CurrencyEvent>): Disposable {

        val timerDisposable = Observable
            .interval(0, 1, TimeUnit.SECONDS)
            .subscribeOn(schedulersProvider.io())
            .subscribe { eventConsumer.accept(RefreshTimePassed) }

        return Disposable {
            timerDisposable.dispose()
        }
    }
}