package com.test.fvba.testapp

import com.test.fvba.testapp.injection.module.APIModule
import com.test.fvba.testapp.injection.module.MainModule
import com.test.fvba.testapp.ui.main.MainFragmentPresenter
import com.test.fvba.testapp.ui.main.MainFragmentPresenterTest
import com.test.fvba.testapp.ui.main.MainFragmentView
import com.test.fvba.testapp.ui.search.SearchFragmentPresenter
import com.test.fvba.testapp.ui.search.SearchFragmentPresenterTest
import com.test.fvba.testapp.ui.search.SearchFragmentView
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [APIModule::class,MainModule::class])
interface ApplicationUnitTestComponent {

    fun inject(test: MainFragmentPresenter<MainFragmentView>)
    fun inject(test: SearchFragmentPresenter<SearchFragmentView>)
    fun inject(test: MainFragmentPresenterTest)
    fun inject(test: SearchFragmentPresenterTest)
}