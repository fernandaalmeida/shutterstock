package com.test.fvba.testapp.util

import android.app.Dialog
import android.content.Context
import android.view.Gravity
import android.widget.FrameLayout
import android.widget.ProgressBar

class ProgressDialog(context: Context) : Dialog(context) {

    init {
        val progressBar = ProgressBar(context)
        val params = FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT)
        params.gravity = Gravity.CENTER
        progressBar.layoutParams = params
        setContentView(progressBar)
    }

    fun showProgressDialog() {
        if (!isShowing) {
            setCanceledOnTouchOutside(false)
            show()
        }
    }

    fun hideProgressDialog() {
        if (isShowing) {
            dismiss()
        }
    }
}