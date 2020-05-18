package com.petergoczan.currenciesmobius.di

import com.petergoczan.currenciesmobius.CurrencyApplication

import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule

@ApplicationScope
@Component(modules = [AndroidSupportInjectionModule::class, AppModule::class, ActivityBinderModule::class])
interface ApplicationComponent : AndroidInjector<CurrencyApplication> {

    @Component.Factory
    interface Factory : AndroidInjector.Factory<CurrencyApplication>
}
