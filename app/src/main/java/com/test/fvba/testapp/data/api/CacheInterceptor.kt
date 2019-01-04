package com.test.fvba.testapp.data.api

import android.content.Context
import com.test.fvba.testapp.exception.NoConnectionException
import com.test.fvba.testapp.util.isConnected
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException
import javax.inject.Inject

/**
 * Interceptor that will be responsible for caching data when device is offline
 */
class CacheInterceptor @Inject internal constructor() : Interceptor {

    companion object {
        private const val CACHE_CONTROL = "Cache-Control"
    }

    @Inject
    lateinit var context: Context

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {

        var request = chain.request()

        request = if (isConnected(context))

            request.newBuilder().header(CACHE_CONTROL, "public, max-age=" + 5).build()
        else
            request.newBuilder().header(CACHE_CONTROL, "public, only-if-cached, max-stale=" + 60 * 60 * 5).build()

        return chain.proceed(request)
    }
}