package com.test.fvba.testapp.ui.main

import com.test.fvba.testapp.data.model.ImagesCategory
import com.test.fvba.testapp.data.model.ImagesResponse
import com.test.fvba.testapp.engine.ImagesEngine
import com.test.fvba.testapp.subscriber.ResponseSubscriber
import com.test.fvba.testapp.ui.base.BasePresenter
import io.reactivex.disposables.CompositeDisposable
import retrofit2.Response
import javax.inject.Inject

class MainFragmentPresenter<V : MainFragmentView> @Inject internal constructor() : BasePresenter<V>(), IMainFragmentPresenter<V> {

    /* Public Attributes ****************************************************************************/

    @Inject
    lateinit var imagesEngine: ImagesEngine

    /* Private Attributes ****************************************************************************/

    private var disposable: CompositeDisposable = CompositeDisposable()

    var imagesList: ArrayList<ImagesCategory> = arrayListOf()

    /* Public Methods *******************************************************************************/
    override fun requestImages() {
        checkViewAttached()
//        setProgressVisible(showProgress)
        val subscribe = imagesEngine.getImagesByCategories(GetImagesCategoriesSubscriber(), GetImagesSubscriber())
        disposable.add(subscribe)
    }

    override fun detachView() {
        super.detachView()
        disposable.clear()
    }

    override fun onErrorHandler(throwable: Throwable) {
        setProgressVisible(false)
        getMvpView().onGenericError()
    }

    override fun updateImagesList(images: ArrayList<ImagesCategory>) {
        imagesList = images
    }

    override fun getImagesRowsCount(position: Int): Int {
        return imagesList[position].images.size
    }

    override fun getImagesCategoryRowsCount(): Int {
        return imagesList.size
    }

    /* Private Methods ******************************************************************************/

    private fun onGetImagesComplete(images: ArrayList<ImagesCategory>) {
        getMvpView().onImagesRequestComplete(images)
    }

    /* Private Inner Classes ******************************************************************************/

    private inner class GetImagesSubscriber : ResponseSubscriber<Response<ImagesResponse>>() {
        override fun onNext(response: Response<ImagesResponse>) {
            if (response.isSuccessful && response.body()!!.data.isEmpty()) {
                getMvpView().onNoResults()
            }
        }


        override fun onNoConnection() {
            getMvpView().onNoConnection()
        }

        override fun onGenericError() {
            getMvpView().onGenericError()
        }
    }

    private inner class GetImagesCategoriesSubscriber : ResponseSubscriber<MutableList<ImagesCategory>>() {

        override fun onNext(response: MutableList<ImagesCategory>) {
            if (response.isNotEmpty()) {
                onGetImagesComplete(ArrayList(response))
            } else {
                getMvpView().onNoResults()
            }
        }

        override fun onNoConnection() {
            getMvpView().onNoConnection()
        }

        override fun onGenericError() {
            getMvpView().onGenericError()
        }
    }

}