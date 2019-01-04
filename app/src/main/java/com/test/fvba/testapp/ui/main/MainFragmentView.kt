package com.test.fvba.testapp.ui.main

import com.test.fvba.testapp.data.model.ImagesCategory
import com.test.fvba.testapp.ui.base.NetworkView

interface MainFragmentView : NetworkView {

    fun onImagesRequestComplete(images: ArrayList<ImagesCategory>)
    fun onNoResults()

}