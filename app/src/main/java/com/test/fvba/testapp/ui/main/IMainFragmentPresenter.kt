package com.test.fvba.testapp.ui.main

import com.test.fvba.testapp.data.model.ImagesCategory
import com.test.fvba.testapp.ui.base.MVPPresenter

interface IMainFragmentPresenter<V : MainFragmentView> : MVPPresenter<V> {
    fun requestImages()
    fun getImagesRowsCount(position: Int): Int
    fun getImagesCategoryRowsCount(): Int
    fun updateImagesList(images: ArrayList<ImagesCategory>)
}