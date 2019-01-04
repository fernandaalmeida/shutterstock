package com.test.fvba.testapp.injection.module

import android.app.Application
import android.content.ContentResolver
import android.content.Context
import android.content.res.Resources
import com.test.fvba.testapp.TestApplication
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ApplicationModule(private val testApp: TestApplication) {

    @Provides @Singleton
    fun provideApplication(): Application = testApp

    @Provides @Singleton
    fun provideContext(): Context = testApp.baseContext

    @Provides @Singleton
    fun provideResources(): Resources = testApp.resources

    @Provides @Singleton
    fun provideContentResolver(context: Context): ContentResolver = context.contentResolver


}