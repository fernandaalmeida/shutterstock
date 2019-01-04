package com.test.fvba.testapp

import com.test.fvba.testapp.activity.MainActivityTest
import com.test.fvba.testapp.injection.component.ApplicationComponent
import com.test.fvba.testapp.injection.module.APIModule
import com.test.fvba.testapp.injection.module.ApplicationModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [APIModule::class, ApplicationModule::class])
interface ApplicationTestComponent : ApplicationComponent {

    fun inject(application: MainActivityTest)
}