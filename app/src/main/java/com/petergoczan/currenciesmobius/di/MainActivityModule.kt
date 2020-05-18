package com.petergoczan.currenciesmobius.di

import com.petergoczan.currenciesmobius.view.MainPageView
import com.petergoczan.currenciesmobius.view.MainPageViewImpl
import dagger.Binds
import dagger.Module

@Module
abstract class MainActivityModule {

    @Binds
    abstract fun view(view: MainPageViewImpl): MainPageView
}