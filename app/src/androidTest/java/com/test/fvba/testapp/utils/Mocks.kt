package com.test.fvba.testapp.utils

import com.test.fvba.testapp.data.model.*

fun mockCategoryResponseSuccesful(): CategoriesResponse {
    val list: MutableList<Category> = mutableListOf()
    list.add(Category("34443", "test"))
    return CategoriesResponse(ArrayList(list))
}

fun mockImageResponseSuccessful(): ImagesResponse {
    val imageData = ImageData("280860062",
            1.5,
            Assets(Preview(300, "https://image.shutterstock.com/display_pic_with_logo/796045/280860062/stock-photo-spain-april-red-motorcycle-ducati-monster-dry-clutch-at-the-pantano-de-puentes-280860062.jpg", 450)),
            "testdescription")
    return ImagesResponse(1, arrayListOf(imageData))
}

fun mockImageResponseNoResult(): ImagesResponse {
     return ImagesResponse(0, arrayListOf<ImageData>())
}

