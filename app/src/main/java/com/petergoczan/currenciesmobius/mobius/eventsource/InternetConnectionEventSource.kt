package com.petergoczan.currenciesmobius.mobius.eventsource

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.os.Build
import com.petergoczan.currenciesmobius.mobius.CurrencyEvent
import com.petergoczan.currenciesmobius.mobius.CurrencyEvent.InternetStateChanged
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
        eventConsumer.accept(InternetStateChanged(isConnected(connectivityManager)))
        return Disposable { connectivityManager.unregisterNetworkCallback(networkCallback) }
    }

    private fun isConnected(connectivityManager: ConnectivityManager): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val networkCapabilities = connectivityManager.activeNetwork ?: return false
            val activeNetwork =
                connectivityManager.getNetworkCapabilities(networkCapabilities) ?: return false
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ||
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)

        } else {
            val activeNetworkInfo = connectivityManager.activeNetworkInfo;
            return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting
        }
    }

    private val networkRequest =
        NetworkRequest
            .Builder()
            .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
            .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
            .addTransportType(NetworkCapabilities.TRANSPORT_ETHERNET)
            .build()

    private val networkCallback = object : ConnectivityManager.NetworkCallback() {

        override fun onLost(network: Network) {
            eventConsumer.accept(InternetStateChanged(false))
        }

        override fun onUnavailable() {
            eventConsumer.accept(InternetStateChanged(false))
        }

        override fun onAvailable(network: Network) {
            eventConsumer.accept(InternetStateChanged(true))
        }
    }

}