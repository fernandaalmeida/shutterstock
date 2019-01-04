package com.test.fvba.testapp.ui.search

import com.test.fvba.testapp.data.model.ImageData
import com.test.fvba.testapp.data.model.ImagesCategory
import com.test.fvba.testapp.ui.base.MVPPresenter

interface ISearchPresenter<V : SearchFragmentView> : MVPPresenter<V> {

    fun requestImages(keyword: String? = null)
    fun getImagesRowsCount(): Int
    fun updateImagesList(images: ArrayList<ImageData>)

}