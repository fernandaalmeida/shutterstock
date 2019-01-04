package com.test.fvba.testapp.subscriber

import com.test.fvba.testapp.data.model.ImagesResponse
import com.test.fvba.testapp.ui.base.NetworkView
import retrofit2.Response

class GetImagesSubscriber(val view: NetworkView) : ResponseSubscriber<Response<ImagesResponse>>() {


    override fun onNoConnection() {
        view.onNoConnection()
    }

    override fun onGenericError() {
        view.onGenericError()
    }
}
