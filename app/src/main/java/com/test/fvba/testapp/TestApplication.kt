package com.test.fvba.testapp

import android.app.Application
import android.content.Context
import com.test.fvba.testapp.injection.component.ApplicationComponent
import com.test.fvba.testapp.injection.component.DaggerApplicationComponent
import com.test.fvba.testapp.injection.module.APIModule
import com.test.fvba.testapp.injection.module.ApplicationModule
import com.test.fvba.testapp.util.Constants.Companion.BASE_URL

open class TestApplication : Application() {

    open lateinit var applicationComponent: ApplicationComponent

    override fun onCreate() {
        super.onCreate()

        applicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(ApplicationModule(this))
                .aPIModule(APIModule(BASE_URL))
                .build()
    }

    companion object {
        fun getApplication(context: Context): TestApplication {
            return context.applicationContext as TestApplication
        }
    }
}