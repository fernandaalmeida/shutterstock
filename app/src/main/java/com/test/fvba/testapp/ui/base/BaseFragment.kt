package com.test.fvba.testapp.ui.base

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.test.fvba.testapp.R
import com.test.fvba.testapp.util.ProgressDialog

abstract class BaseFragment: Fragment(), NetworkView {

    private lateinit var mProgress: ProgressDialog

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        mProgress = ProgressDialog(context!!)
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onPause() {
        super.onPause()
        hideProgress()
    }

    fun showSnackBar(message: String) {
        Snackbar.make(activity!!.window.decorView.rootView, message, Snackbar.LENGTH_LONG)
                .show()
    }

    override fun showProgress() {
        mProgress.showProgressDialog()
    }

    override fun onGenericError() {
        showSnackBar(getString(R.string.snackbar_generic_error))
    }

    override fun onNoConnection() {
        showSnackBar(getString(R.string.snackbar_no_connection))
    }

    override fun hideProgress() {
        mProgress.hideProgressDialog()
    }


}