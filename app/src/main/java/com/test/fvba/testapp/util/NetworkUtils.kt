package com.test.fvba.testapp.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo

/**
 * Check if network is available
 *
 * @param context Application context
 *
 * @return true if connected, false otherwise.
 */
fun isConnected(context: Context): Boolean {
    val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val activeNetwork: NetworkInfo? = connectivityManager.activeNetworkInfo
    return activeNetwork != null && activeNetwork.isConnected
}