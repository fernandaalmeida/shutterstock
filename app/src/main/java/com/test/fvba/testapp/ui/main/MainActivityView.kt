package com.test.fvba.testapp.ui.main

import com.test.fvba.testapp.ui.base.NetworkView

interface MainActivityView : NetworkView {
    fun onSearch(query: String)
}