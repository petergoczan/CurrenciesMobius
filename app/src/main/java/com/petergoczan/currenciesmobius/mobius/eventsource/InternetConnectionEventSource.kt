package com.petergoczan.currenciesmobius.mobius.eventsource

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import com.petergoczan.currenciesmobius.mobius.CurrencyEvent
import com.spotify.mobius.EventSource
import com.spotify.mobius.disposables.Disposable
import com.spotify.mobius.functions.Consumer
import javax.inject.Inject


class InternetConnectionEventSource @Inject constructor(private val context: Context) :
    EventSource<CurrencyEvent> {

    private lateinit var eventConsumer: Consumer<CurrencyEvent>

    override fun subscribe(eventConsumer: Consumer<CurrencyEvent>): Disposable {
        this.eventConsumer = eventConsumer
        val connectivityManager =
            context.applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        connectivityManager.registerNetworkCallback(networkRequest, networkCallback)
        return Disposable { connectivityManager.unregisterNetworkCallback(networkCallback) }
    }

    private val networkRequest =
        NetworkRequest
            .Builder()
            .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
            .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
            .build()

    private val networkCallback = object : ConnectivityManager.NetworkCallback() {
        override fun onLosing(network: Network, maxMsToLive: Int) {
            eventConsumer.accept(CurrencyEvent.InternetStateChanged(false))
        }

        override fun onLost(network: Network) {
           eventConsumer.accept(CurrencyEvent.InternetStateChanged(false))
        }

        override fun onUnavailable() {
            eventConsumer.accept(CurrencyEvent.InternetStateChanged(false))
        }

        override fun onAvailable(network: Network) {
            eventConsumer.accept(CurrencyEvent.InternetStateChanged(true))
        }
    }

}