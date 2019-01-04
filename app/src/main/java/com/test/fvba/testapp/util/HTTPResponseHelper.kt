package com.test.fvba.testapp.util

import java.net.SocketTimeoutException
import java.net.UnknownHostException

import okhttp3.MediaType
import okhttp3.ResponseBody
import retrofit2.Response
import rx.Observable

/**
 * HTTP response handling.
 */
object HTTPResponseHelper {

    fun parseError(throwable: Throwable): Int {
        if (throwable is SocketTimeoutException) {
            return Constants.HTTP_ERROR_CODE_TIMEOUT
        } else if (throwable is UnknownHostException) {
            return Constants.ERROR_NO_CONNECTION
        }

        return Constants.HTTP_GENERIC_ERROR_CODE
    }

    fun <T> getError(throwable: Throwable): Response<T> {
        return Response.error(Constants.HTTP_GENERIC_ERROR_CODE, ResponseBody
                .create(MediaType.parse("application/json"),
                        "{\"error\":[\"" + throwable.localizedMessage + "\"]}"))
    }

    fun <T> createErrorResponse(throwable: Throwable): Observable<Response<Any>> {
        return Observable.just(getError(throwable)
        )
    }
}

