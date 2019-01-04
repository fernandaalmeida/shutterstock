package com.test.fvba.testapp.data.api

import com.test.fvba.testapp.data.model.CategoriesResponse
import com.test.fvba.testapp.data.model.ImagesResponse
import io.reactivex.Flowable
import io.reactivex.Observable
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

open interface ApiService{

    @GET("v2/images/search")
    open fun getImages(@Query("query") query: String?): Flowable<Response<ImagesResponse>>

    @GET("v2/images/categories")
    open fun getCategories(): Flowable<Response<CategoriesResponse>>

}