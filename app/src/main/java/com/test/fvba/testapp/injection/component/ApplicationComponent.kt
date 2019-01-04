package com.test.fvba.testapp.injection.component

import com.test.fvba.testapp.injection.module.APIModule
import com.test.fvba.testapp.injection.module.ApplicationModule
import com.test.fvba.testapp.ui.main.MainActivity
import com.test.fvba.testapp.ui.main.MainFragment
import com.test.fvba.testapp.ui.search.SearchFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [APIModule::class, ApplicationModule::class])
interface ApplicationComponent {
    fun inject(activity: MainActivity)
    fun inject(fragment: MainFragment)
    fun inject(fragment: SearchFragment)

}