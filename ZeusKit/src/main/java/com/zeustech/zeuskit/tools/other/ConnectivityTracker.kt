package com.zeustech.zeuskit.tools.other

import android.app.Application
import android.content.Context
import android.net.*
import android.os.Build
import androidx.annotation.RequiresPermission
import androidx.annotation.VisibleForTesting
import androidx.lifecycle.LiveData

/**
 * A LiveData class which wraps the network connection status
 * Requires Permission: ACCESS_NETWORK_STATE
 *
 * See https://developer.android.com/training/monitoring-device-state/connectivity-monitoring
 * See https://developer.android.com/reference/android/net/ConnectivityManager
 * See https://developer.android.com/reference/android/net/ConnectivityManager#CONNECTIVITY_ACTION
 */

class ConnectivityTracker @VisibleForTesting internal constructor(
    private val connectivityManager: ConnectivityManager
): LiveData<Boolean>() {

    @RequiresPermission(android.Manifest.permission.ACCESS_NETWORK_STATE)
    constructor(application: Application) : this(application.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager)

    private val networkCallback = object : ConnectivityManager.NetworkCallback() {

        override fun onAvailable(network: Network) {
            postValue(true)
        }

        override fun onLost(network: Network) {
            postValue(false)
        }
    }

    /** Returns connection type. 0: none; 1: mobile data; 2: wifi; 3:vpn **/
    private fun getConnectionType(): Int {
        var result = 0
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)?.let {
                when {
                    it.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> { result = 2 }
                    it.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> { result = 1 }
                    it.hasTransport(NetworkCapabilities.TRANSPORT_VPN) -> { result = 3 }
                }
            }
        } else {
            connectivityManager.activeNetworkInfo?.let {
                // it.isConnected && one of above
                when (it.type) {
                    ConnectivityManager.TYPE_WIFI -> { result = 2 }
                    ConnectivityManager.TYPE_MOBILE -> { result = 1 }
                    ConnectivityManager.TYPE_VPN -> { result = 3 }
                }
            }
        }
        return result
    }

    fun isConnected() = getConnectionType() != 0

    override fun onActive() {
        super.onActive()
        postValue(getConnectionType() != 0)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            connectivityManager.registerDefaultNetworkCallback(networkCallback)
        } else {
            val builder = NetworkRequest.Builder()
            connectivityManager.registerNetworkCallback(builder.build(), networkCallback)
        }
    }

    override fun onInactive() {
        super.onInactive()
        connectivityManager.unregisterNetworkCallback(networkCallback)
    }

}