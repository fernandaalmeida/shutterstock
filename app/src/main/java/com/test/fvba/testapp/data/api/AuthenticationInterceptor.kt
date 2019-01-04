package com.test.fvba.testapp.data.api

import com.test.fvba.testapp.util.Constants
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

class AuthenticationInterceptor(private val authToken: String) : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()

        val builder = original.newBuilder()
                .header(Constants.HEADER_AUTH, authToken)

        val request = builder.build()
        return chain.proceed(request)
    }
}