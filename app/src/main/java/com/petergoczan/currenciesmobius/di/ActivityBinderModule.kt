package com.petergoczan.currenciesmobius.di

import com.petergoczan.currenciesmobius.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBinderModule {

    @ContributesAndroidInjector
    internal abstract fun bindMainActivity(): MainActivity
}