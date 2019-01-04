package com.test.fvba.testapp.ui.base


/**
 * Every presenter in the app must either implement this interface or extend BasePresenter
 * indicating the MVPView type that wants to be attached with.
 */
interface MVPPresenter<V : MVPView> {

    fun attachView(view: V?)

    fun detachView()

}