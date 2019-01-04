package com.test.fvba.testapp.ui.base


import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.view.View

import com.test.fvba.testapp.R
import com.test.fvba.testapp.util.ProgressDialog


open class BaseNetworkActivity : AppCompatActivity(), NetworkView {

    private lateinit var mProgress: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mProgress = ProgressDialog(this)
    }

    override fun showProgress() {
        mProgress.showProgressDialog()
    }

    override fun hideProgress() {
        mProgress.hideProgressDialog()
    }

    private fun showSnackBar(message: String) {
        Snackbar.make(findViewById<View>(R.id.root), message, Snackbar.LENGTH_LONG)
                .show()
    }


    override fun onPause() {
        super.onPause()
        hideProgress()
    }

    override fun onGenericError() {
        mProgress.hideProgressDialog()
        showSnackBar(getString(R.string.snackbar_generic_error))
    }

    override fun onNoConnection() {
        mProgress.hideProgressDialog()
        showSnackBar(getString(R.string.snackbar_no_connection))
    }

}
