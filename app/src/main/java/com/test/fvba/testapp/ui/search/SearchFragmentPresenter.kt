package com.test.fvba.testapp.ui.search

import com.test.fvba.testapp.data.model.ImageData
import com.test.fvba.testapp.data.model.ImagesResponse
import com.test.fvba.testapp.engine.ImagesEngine
import com.test.fvba.testapp.subscriber.ResponseSubscriber
import com.test.fvba.testapp.ui.base.BasePresenter
import io.reactivex.disposables.CompositeDisposable
import retrofit2.Response
import javax.inject.Inject

class SearchFragmentPresenter<V : SearchFragmentView> @Inject internal constructor() : BasePresenter<V>(), ISearchPresenter<V> {

    /* Public Attributes ****************************************************************************/

    @Inject
    lateinit var imagesEngine: ImagesEngine

    /* Private Attributes ****************************************************************************/

    private var disposable: CompositeDisposable = CompositeDisposable()

    var imagesList: ArrayList<ImageData> = arrayListOf()

    /* Public Methods *******************************************************************************/
    override fun requestImages(keyword: String?) {
        checkViewAttached()
        val subscribe = imagesEngine.getImages(GetImagesSubscriber(), keyword)
        disposable.add(subscribe!!)

    }

    override fun detachView() {
        super.detachView()
        disposable.clear()
    }

    override fun onErrorHandler(throwable: Throwable) {
        setProgressVisible(false)
        getMvpView().onGenericError()
    }

    override fun updateImagesList(images: ArrayList<ImageData>) {
        imagesList = images
    }

    override fun getImagesRowsCount(): Int {
        return imagesList.size
    }

    /* Private Methods ******************************************************************************/

    private fun onGetImagesComplete(response: ArrayList<ImageData>) {
        getMvpView().onImagesRequestComplete(response)
    }

    /* Private Inner Classes ******************************************************************************/

    private inner class GetImagesSubscriber : ResponseSubscriber<Response<ImagesResponse>>() {

        override fun onNext(response: Response<ImagesResponse>) {
            if (response.isSuccessful) {
                if (response.body() != null) {
                    if (response.body()!!.data.isNotEmpty()) {
                        onGetImagesComplete(response.body()!!.data)
                    } else {
                        onNoResultsFound()
                    }

                } else {
                    getMvpView().onGenericError()
                }

            } else {
                getMvpView().onGenericError()
            }
        }

        override fun onNoConnection() {
            getMvpView().onNoConnection()
        }

        override fun onGenericError() {
            getMvpView().onGenericError()
        }
    }

    private fun onNoResultsFound() {
        getMvpView().onNoResultsFound()
    }


}