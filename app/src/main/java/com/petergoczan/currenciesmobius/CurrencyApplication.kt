package com.petergoczan.currenciesmobius

import com.petergoczan.currenciesmobius.di.DaggerApplicationComponent
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication

class CurrencyApplication : DaggerApplication() {

    override fun applicationInjector(): AndroidInjector<CurrencyApplication> {
        return DaggerApplicationComponent.factory().create(this)
    }
}