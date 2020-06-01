package com.petergoczan.currenciesmobius.di

import com.petergoczan.currenciesmobius.communication.CurrenciesEndpoint
import com.petergoczan.currenciesmobius.presentation.MainPagePresenter
import com.petergoczan.currenciesmobius.presentation.MainPagePresenterImpl
import com.petergoczan.currenciesmobius.view.MainPageView
import com.petergoczan.currenciesmobius.view.MainPageViewImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

private const val CURRENCIES_BASE_URL = "https://hiring.revolut.codes/api/android/"

@Module
abstract class MainActivityModule {

    @Binds
    internal abstract fun view(view: MainPageViewImpl): MainPageView

    @Binds
    internal abstract fun presenter(presenter: MainPagePresenterImpl): MainPagePresenter

    @Module
    companion object {

        @JvmStatic
        @Provides
        @ActivityScope
        internal fun currenciesEndpoint(retrofit: Retrofit.Builder): CurrenciesEndpoint {
            return retrofit
                .baseUrl(CURRENCIES_BASE_URL)
                .build()
                .create(CurrenciesEndpoint::class.java)
        }
    }
}