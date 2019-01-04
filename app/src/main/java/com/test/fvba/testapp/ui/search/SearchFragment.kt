package com.test.fvba.testapp.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.test.fvba.testapp.R
import com.test.fvba.testapp.TestApplication
import com.test.fvba.testapp.data.model.ImageData
import com.test.fvba.testapp.ui.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_search.*
import kotlinx.android.synthetic.main.fragment_search.view.*
import javax.inject.Inject

class SearchFragment: BaseFragment(), SearchFragmentView {


    /* Private Attributes ****************************************************************************/

    @Inject
    lateinit var presenter: SearchFragmentPresenter<SearchFragmentView>
    private var query:String? = null
    private lateinit var  adapter: ImagesAdapter
    private lateinit var rootView: View

    /* Public Static Methods *******************************************************************************/

    companion object {
        private const val QUERY_PARAM: String = "queryParam"

        fun newInstance(query: String): SearchFragment = SearchFragment().apply {
            arguments = Bundle().apply {
                putString(QUERY_PARAM, query)
            }
        }
    }

    /* Public Methods *******************************************************************************/

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initFromArguments()
        adapter = ImagesAdapter(presenter)
        rv_images_list.adapter = adapter
        presenter.requestImages(query)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        presenter.attachView(this)
        rootView = inflater.inflate(R.layout.fragment_search, container, false)
        return rootView

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        TestApplication.getApplication(context!!).applicationComponent.inject(this)

    }

    override fun onImagesRequestComplete(imagesList: ArrayList<ImageData>) {
        hideProgress()
        rootView.empty_screen_search.visibility = View.GONE
        adapter.updateData(imagesList)
    }

    override fun onGenericError() {
        showSnackBar(getString(R.string.snackbar_generic_error))
    }

    override fun onNoResultsFound() {
        hideProgress()
        rootView.empty_screen_search.visibility = View.VISIBLE
    }

    override fun onNoConnection() {
       showSnackBar(getString(R.string.snackbar_no_connection))
    }

    /* Private Methods *******************************************************************************/

    private fun initFromArguments() {
        arguments?.getString(QUERY_PARAM)?.let {
            query = it
        }
    }
}