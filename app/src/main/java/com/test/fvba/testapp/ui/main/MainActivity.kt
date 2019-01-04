package com.test.fvba.testapp.ui.main

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.SearchView
import android.view.Menu
import com.test.fvba.testapp.R
import com.test.fvba.testapp.TestApplication
import com.test.fvba.testapp.ui.base.BaseNetworkActivity
import com.test.fvba.testapp.ui.search.SearchFragment
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : BaseNetworkActivity(), MainActivityView {

    /* Private Static Attributes ****************************************************************************/

    companion object {

        const val KEY_IMAGE_LIST = "keyImageList"

    }

    /* Public Attributes ****************************************************************************/

    /* Private Attributes ****************************************************************************/


    /* Public Methods *******************************************************************************/

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        TestApplication.getApplication(this).applicationComponent.inject(this)
        setSupportActionBar(toolbar)

        supportFragmentManager
                .beginTransaction()
                .replace(R.id.content, MainFragment.newInstance())
                .commit()
    }

    override fun onNewIntent(intent: Intent) {
        setIntent(intent)
        handleIntent(intent)
    }

    private fun handleIntent(intent: Intent) {
        if (Intent.ACTION_SEARCH == intent.action) {
            intent.getStringExtra(SearchManager.QUERY)?.also { query ->
                onSearch(query)
            }
        }
    }

    override fun onSearch(query: String) {
        supportFragmentManager
                .beginTransaction()
                .replace(R.id.content, SearchFragment.newInstance(query))
                .commit()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.search_menu, menu)

        // Associate searchable configuration with the SearchView
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        (menu.findItem(R.id.search).actionView as SearchView).apply {
            setSearchableInfo(searchManager.getSearchableInfo(componentName))
        }

        return true
    }




}
