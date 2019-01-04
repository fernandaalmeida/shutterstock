package com.test.fvba.testapp.ui.base


/**
 * Base class that implements the Presenter interface and provides a base implementation for
 * attachView() and detachView(). It also handles keeping a reference to the mvpView that
 * can be accessed from the children classes by calling getMvpView().
 */
abstract class BasePresenter<V : MVPView> : MVPPresenter<V> {

    companion object {
        private const val ATTACHED_EXCEPTION_MESSAGE = "Please call Presenter.attachView(MvpView) before requesting data to the Presenter"
    }

    private var view: V? = null
    private val isViewAttached: Boolean get() = view != null

    override fun attachView(view: V?) {
        this.view = view
    }

    override fun detachView() {
        view = null
    }

    protected abstract fun onErrorHandler(throwable: Throwable)

    protected fun checkViewAttached() {
        if (!isViewAttached) throw MvpViewNotAttachedException()
    }

    private class MvpViewNotAttachedException internal constructor() : RuntimeException(ATTACHED_EXCEPTION_MESSAGE)

    protected fun setProgressVisible(visible: Boolean) {
        if (visible) {
            view?.showProgress()
        } else {
            view?.hideProgress()
        }
    }

    fun getMvpView(): V {
        return view!!
    }

}
