package com.petergoczan.currenciesmobius

import com.spotify.mobius.rx2.RxMobius
import io.reactivex.ObservableTransformer

class CurrencyEffectHandler {
    fun build(): ObservableTransformer<CurrencyEffect, CurrencyEvent> = RxMobius
        .subtypeEffectHandler<CurrencyEffect, CurrencyEvent>()
        .build()
}
