package com.gitsurfer.gitsurf.model.network

import android.annotation.SuppressLint
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkRequest
import timber.log.Timber

class NetworkManager(private val connectivityManager: ConnectivityManager) {

  private val listeners by lazy { hashSetOf<NetworkListener>() }
  private var networkCallbackListener: NetworkCallbackListener? = null

  @SuppressLint("MissingPermission")
  fun hasInternetAccess() =
    connectivityManager.activeNetworkInfo?.isConnected == true

  @SuppressLint("MissingPermission")
  fun registerNetworkListener(networkListener: NetworkListener) {
    listeners.add(networkListener)
    Timber.d("Network listener $networkListener registered")
    synchronized(this) {
      if (networkCallbackListener == null) {
        networkCallbackListener = NetworkCallbackListener()
        connectivityManager.registerNetworkCallback(
            NetworkRequest.Builder()
                .build(),
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
    override fun onAvailable(network: Network?) {
      super.onAvailable(network)
      synchronized(this@NetworkManager) {
        if (hasInternetAccess()) {
          Timber.d("Network onAvailable $network")
          listeners.forEach { it.onAvailability(hasInternetAccess(), network) }
        }
      }
    }

    override fun onLost(network: Network?) {
      super.onLost(network)
      synchronized(this@NetworkManager) {
        if (!hasInternetAccess()) {
          Timber.d("Network onLost $network")
          listeners.forEach { it.onAvailability(!hasInternetAccess(), network) }
        }
      }
    }
  }

  interface NetworkListener {
    fun onAvailability(
      isAvailable: Boolean,
      network: Network?
    )
  }
}