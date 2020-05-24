package com.petergoczan.currenciesmobius

import com.spotify.mobius.rx2.RxMobius
import io.reactivex.ObservableTransformer

fun createEffectHandlers(): ObservableTransformer<CurrencyEffect, CurrencyEvent> = RxMobius
    .subtypeEffectHandler<CurrencyEffect, CurrencyEvent>()
    .build()

