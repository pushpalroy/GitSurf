package com.gitsurfer.gitsurf.data.network

import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.os.Build
import timber.log.Timber

class NetworkManager(private val connectivityManager: ConnectivityManager) {

  private val listeners by lazy { hashSetOf<NetworkListener>() }
  private lateinit var networkCallbackListener: NetworkCallbackListener

  fun hasInternetAccess(): Boolean {

    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
      val networkInfo = connectivityManager.activeNetworkInfo
      return networkInfo != null && networkInfo.isConnected
    }

    val networks = connectivityManager.allNetworks
    var hasInternet = false
    if (networks.isNotEmpty()) {
      for (network in networks) {
        connectivityManager.getNetworkCapabilities(network)?.let { capabilities ->
          if (capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)) {
            hasInternet = true
          }
        }
      }
    }
    return hasInternet
  }

  fun registerNetworkListener(networkListener: NetworkListener?) {
    networkListener?.let {
      listeners.add(networkListener)
      Timber.d("Network listener $networkListener registered")
      synchronized(this) {
        networkCallbackListener = NetworkCallbackListener()
        connectivityManager.registerNetworkCallback(
          NetworkRequest.Builder().build(),
          networkCallbackListener
        )
        networkCallbackListener
      }
    }
  }

  fun unregisterNetworkListener(networkListener: NetworkListener) {
    if (listeners.contains(networkListener)) {
      listeners.remove(networkListener)
      Timber.d("Network listener $networkListener removed")
    }
  }

  inner class NetworkCallbackListener : ConnectivityManager.NetworkCallback() {

    override fun onAvailable(network: Network) {
      super.onAvailable(network)
      synchronized(this@NetworkManager) {
        if (hasInternetAccess()) {
          Timber.d("Network onAvailable $network")
          listeners.forEach { it.onAvailability(isAvailable = true) }
        }
      }
    }

    override fun onLost(network: Network) {
      super.onLost(network)
      synchronized(this@NetworkManager) {
        if (!hasInternetAccess()) {
          Timber.d("Network onLost $network")
          listeners.forEach { it.onAvailability(isAvailable = false) }
        }
      }
    }
  }

  interface NetworkListener {
    fun onAvailability(
      isAvailable: Boolean
    )
  }
}