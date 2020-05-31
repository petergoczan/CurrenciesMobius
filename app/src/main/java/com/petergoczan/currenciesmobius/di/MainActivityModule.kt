package com.petergoczan.currenciesmobius.di

import com.petergoczan.currenciesmobius.presentation.MainPagePresenter
import com.petergoczan.currenciesmobius.presentation.MainPagePresenterImpl
import com.petergoczan.currenciesmobius.view.MainPageView
import com.petergoczan.currenciesmobius.view.MainPageViewImpl
import dagger.Binds
import dagger.Module

@Module
abstract class MainActivityModule {

    @Binds
    internal abstract fun view(view: MainPageViewImpl): MainPageView

    @Binds
    internal abstract fun presenter(presenter: MainPagePresenterImpl): MainPagePresenter
}