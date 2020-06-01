package com.petergoczan.currenciesmobius.mobius.effecthandler

import android.util.Log
import com.petergoczan.currenciesmobius.communication.CurrenciesEndpoint
import com.petergoczan.currenciesmobius.di.ActivityScope
import com.petergoczan.currenciesmobius.mobius.CurrencyEffect
import com.petergoczan.currenciesmobius.mobius.CurrencyEvent
import com.petergoczan.currenciesmobius.scheduler.SchedulersProvider
import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.ObservableTransformer
import javax.inject.Inject

@ActivityScope
class RequestDataEffectHandler @Inject constructor(
    private val currenciesEndpoint: CurrenciesEndpoint,
    private val schedulersProvider: SchedulersProvider
) :
    ObservableTransformer<CurrencyEffect.RequestData, CurrencyEvent> {

    override fun apply(upstream: Observable<CurrencyEffect.RequestData>): ObservableSource<CurrencyEvent> {
        return upstream.flatMap {
            currenciesEndpoint
                .getCurrencies(it.baseCurrencyCode)
                .subscribeOn(schedulersProvider.io())
                .map { model ->
                    Log.d("CurrencyMobius", "data arrived: $model")
                    CurrencyEvent.DataArrived(model)
                }
        }
    }
}