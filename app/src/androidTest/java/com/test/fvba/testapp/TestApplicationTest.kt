package com.test.fvba.testapp

import com.test.fvba.testapp.injection.component.ApplicationComponent
import com.test.fvba.testapp.injection.module.ApplicationModule
import com.test.fvba.testapp.module.APITestModule
import com.test.fvba.testapp.util.Constants

open class TestApplicationTest : TestApplication() {

    override var applicationComponent: ApplicationComponent
        get() = DaggerApplicationTestComponent.builder()
                .aPIModule(APITestModule(Constants.BASE_URL))
                .applicationModule(ApplicationModule(this))
                .build()
        set(value) {}

}