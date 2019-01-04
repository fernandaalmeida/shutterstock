package com.test.fvba.testapp.ui.main.databinding

import android.databinding.BindingAdapter
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.test.fvba.testapp.R
import com.test.fvba.testapp.data.model.ImageData
import com.test.fvba.testapp.util.Constants.Companion.IMAGE_DEFAULT_SIZE

object ImageBindingAdapter {

    @JvmStatic
    @BindingAdapter("imageUrl")
    fun setImageUrl(view: ImageView, image: ImageData) {
        val options = RequestOptions()
                .error(R.drawable.ic_launcher_background)
                .placeholder(R.drawable.ic_launcher_background)
                .override(image.getRoundProportionateWidth(IMAGE_DEFAULT_SIZE),IMAGE_DEFAULT_SIZE)

        Glide.with(view.context).setDefaultRequestOptions(options).load(image.assets.preview.url).into(view)
    }
}