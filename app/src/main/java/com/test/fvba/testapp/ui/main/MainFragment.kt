package com.test.fvba.testapp.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.test.fvba.testapp.R
import com.test.fvba.testapp.TestApplication
import com.test.fvba.testapp.data.model.ImagesCategory
import com.test.fvba.testapp.ui.base.BaseFragment
import kotlinx.android.synthetic.main.content_main.*
import javax.inject.Inject

class MainFragment: BaseFragment(), MainFragmentView  {

    /* Public Attributes ****************************************************************************/

    @Inject
    lateinit var presenter: MainFragmentPresenter<MainFragmentView>

    /* Private Attributes ****************************************************************************/

    private lateinit var adapter: CategoryImagesAdapter

    /* Public Static Methods *******************************************************************************/

    companion object {

        fun newInstance(): MainFragment = MainFragment()

    }

    /* Public Methods *******************************************************************************/

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (savedInstanceState != null) {
            presenter.updateImagesList(savedInstanceState.getParcelableArrayList(MainActivity.KEY_IMAGE_LIST))
        } else {
            showProgress()
            presenter.requestImages()
        }

        activateSwipeRefresh()

        adapter = CategoryImagesAdapter(presenter, context!!)
        rv_list.adapter = adapter
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelableArrayList(MainActivity.KEY_IMAGE_LIST, presenter.imagesList)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        presenter.attachView(this)
        return inflater.inflate(R.layout.content_main, container, false)

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        TestApplication.getApplication(context!!).applicationComponent.inject(this)

    }

    override fun onNoResults() {
        empty_screen.visibility = View.VISIBLE
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.detachView()
    }

    override fun onImagesRequestComplete(images: ArrayList<ImagesCategory>) {
        hideProgress()
        empty_screen.visibility = View.GONE
        adapter.updateData(images)
    }

    override fun onGenericError() {
        super.onGenericError()
        empty_screen.visibility = View.GONE
        stopSwipeRefresh()
    }


    /* Private Methods ******************************************************************************/
    private fun stopSwipeRefresh() {
        if (srl_swipe != null) {
            srl_swipe.isRefreshing = false
        }
    }

    private fun activateSwipeRefresh() {
        srl_swipe.setOnRefreshListener {
            presenter.requestImages()
        }
    }

}