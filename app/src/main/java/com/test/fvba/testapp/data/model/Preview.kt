package com.test.fvba.testapp.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Preview (
        val height: Int,
        val url: String,
        val width: Int
): Parcelable
