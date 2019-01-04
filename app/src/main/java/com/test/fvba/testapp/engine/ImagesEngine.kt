package com.test.fvba.testapp.engine

import com.test.fvba.testapp.data.api.ApiService
import com.test.fvba.testapp.data.model.ImageData
import com.test.fvba.testapp.data.model.ImagesCategory
import com.test.fvba.testapp.data.model.ImagesResponse
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subscribers.DisposableSubscriber
import retrofit2.Response
import javax.inject.Inject

class ImagesEngine @Inject internal constructor() {

    @Inject
    lateinit var apiService: ApiService

    companion object {
        private const val CATEGORY_QUERY = "category="
    }

    fun getImages(subscriber: DisposableSubscriber<Response<ImagesResponse>>, imageKeyword: String? = null): Disposable? =
            apiService.getImages(imageKeyword)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeWith(subscriber)


    private fun getImagesObservable(subscriber: DisposableSubscriber<Response<ImagesResponse>>, imageKeyword: String? = null): Flowable<Response<ImagesResponse>>? =
            apiService.getImages(imageKeyword)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread()).doOnError { e -> subscriber.onError(e) }


    fun getImagesByCategories(subscriber: DisposableSubscriber<MutableList<ImagesCategory>>,
                              subscriberImages: DisposableSubscriber<Response<ImagesResponse>>): Disposable {
        val imagesCategory = mutableListOf<ImagesCategory>()


        return apiService.getCategories()
                .flatMapIterable { categories -> categories.body()!!.data }
                .flatMap { e ->
                    val imageDataList = mutableListOf<ImageData>()
                    imagesCategory.add(ImagesCategory(e, imageDataList))
                    getImagesObservable(subscriberImages, "$CATEGORY_QUERY ${e.id}")!!
                            .map { image ->
                                imageDataList.addAll(image.body()!!.data)
                            }
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .flatMap {
                                Flowable.just(imagesCategory)
                            }
                }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(subscriber)
    }

}