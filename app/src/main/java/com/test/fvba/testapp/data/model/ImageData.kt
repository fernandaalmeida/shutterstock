package com.test.fvba.testapp.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import kotlin.math.roundToInt

@Parcelize
data class ImageData(
    val id: String,
    val aspect: Double,
    val assets: Assets,
    val description: String
): Parcelable{

    fun getRoundProportionateWidth(width: Int): Int{
        return aspect.times(width).roundToInt()
    }

}