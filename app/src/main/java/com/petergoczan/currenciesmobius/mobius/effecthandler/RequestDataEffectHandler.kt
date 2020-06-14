package com.petergoczan.currenciesmobius.mobius.effecthandler

import com.petergoczan.currenciesmobius.communication.CurrenciesEndpoint
import com.petergoczan.currenciesmobius.data.RemoteModelToCurrencyListItemsTransformer
import com.petergoczan.currenciesmobius.di.ActivityScope
import com.petergoczan.currenciesmobius.mobius.CurrencyEffect.RequestData
import com.petergoczan.currenciesmobius.mobius.CurrencyEvent
import com.petergoczan.currenciesmobius.mobius.CurrencyEvent.*
import com.petergoczan.currenciesmobius.scheduler.SchedulersProvider
import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.ObservableTransformer
import javax.inject.Inject

@ActivityScope
class RequestDataEffectHandler @Inject constructor(
    private val currenciesEndpoint: CurrenciesEndpoint,
    private val schedulersProvider: SchedulersProvider,
    private val remoteModelModelToCurrencyListItemsTransformer: RemoteModelToCurrencyListItemsTransformer
) :
    ObservableTransformer<RequestData, CurrencyEvent> {

    override fun apply(upstream: Observable<RequestData>): ObservableSource<CurrencyEvent> {
        return upstream.flatMap {
            currenciesEndpoint
                .getCurrencies(it.baseCurrencyCode)
                .subscribeOn(schedulersProvider.io())
                .compose(remoteModelModelToCurrencyListItemsTransformer)
                .map { model -> DataArrived(model) as CurrencyEvent }
                .onErrorReturn { CommunicationError }
        }
    }
}