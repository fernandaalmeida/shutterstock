package com.test.fvba.testapp.ui.search

import com.test.fvba.testapp.data.model.ImageData
import com.test.fvba.testapp.data.model.ImagesCategory
import com.test.fvba.testapp.ui.base.NetworkView

interface SearchFragmentView  : NetworkView {
    fun onImagesRequestComplete(imagesList: ArrayList<ImageData>)
    fun onNoResultsFound()
}