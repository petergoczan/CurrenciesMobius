package com.petergoczan.currenciesmobius.communication

import com.petergoczan.currenciesmobius.mobius.RemoteCurrenciesModel
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface CurrenciesEndpoint {

    @GET("latest")
    fun getCurrencies(@Query("base") query: String): Observable<RemoteCurrenciesModel>
}