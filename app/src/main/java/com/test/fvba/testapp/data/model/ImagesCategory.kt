package com.test.fvba.testapp.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue

@Parcelize
data class ImagesCategory(
        val category: Category,
         val images: MutableList<ImageData>
):Parcelable