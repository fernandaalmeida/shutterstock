package com.test.fvba.testapp.subscriber

import com.test.fvba.testapp.util.Constants
import com.test.fvba.testapp.util.HTTPResponseHelper
import io.reactivex.subscribers.DisposableSubscriber

/**
 * Default subscriber base class to be used whenever you want default error handling.
 */
abstract class ResponseSubscriber<T> : DisposableSubscriber<T>() {
    override fun onComplete() {
        // no-op by default.
    }

    override fun onError(e: Throwable) {
        onResponseError(e)
    }



    override fun onNext(response: T) {
        // no-op by default.
    }

    private fun onResponseError(throwable: Throwable) {
        val resultCode = HTTPResponseHelper.parseError(throwable)
        when (resultCode) {
            Constants.HTTP_ERROR_CODE_TIMEOUT, Constants.ERROR_NO_CONNECTION -> onNoConnection()
            else -> onGenericError()
        }
    }

    abstract fun onNoConnection()

    abstract fun onGenericError()
}