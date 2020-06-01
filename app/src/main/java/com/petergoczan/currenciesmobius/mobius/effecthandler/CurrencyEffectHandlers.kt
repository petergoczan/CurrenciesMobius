package com.petergoczan.currenciesmobius.mobius.effecthandler

import com.petergoczan.currenciesmobius.di.ActivityScope
import com.petergoczan.currenciesmobius.mobius.CurrencyEffect
import com.petergoczan.currenciesmobius.mobius.CurrencyEvent
import com.spotify.mobius.rx2.RxMobius
import io.reactivex.ObservableTransformer
import javax.inject.Inject

@ActivityScope
class CurrencyEffectHandlers @Inject constructor(
    private val requestDataEffectHandler: RequestDataEffectHandler
) {

    fun createEffectHandlers(): ObservableTransformer<CurrencyEffect, CurrencyEvent> =
        RxMobius
            .subtypeEffectHandler<CurrencyEffect, CurrencyEvent>()
            .addTransformer<CurrencyEffect.RequestData>(
                CurrencyEffect.RequestData::class.java,
                requestDataEffectHandler
            )
            .build()

}