package com.test.fvba.testapp.activity

import android.app.Instrumentation
import android.content.Intent
import android.support.test.InstrumentationRegistry
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.*
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers
import android.support.test.espresso.matcher.ViewMatchers.withEffectiveVisibility
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import android.view.KeyEvent
import com.test.fvba.testapp.ApplicationTestComponent
import com.test.fvba.testapp.DaggerApplicationTestComponent
import com.test.fvba.testapp.R
import com.test.fvba.testapp.R.id.rv_list
import com.test.fvba.testapp.TestApplication
import com.test.fvba.testapp.data.api.ApiService
import com.test.fvba.testapp.data.model.CategoriesResponse
import com.test.fvba.testapp.data.model.ImagesResponse
import com.test.fvba.testapp.injection.module.ApplicationModule
import com.test.fvba.testapp.module.APITestModule
import com.test.fvba.testapp.runner.TestApplicationTestRunner
import com.test.fvba.testapp.ui.main.MainActivity
import com.test.fvba.testapp.util.Constants
import com.test.fvba.testapp.utils.CustomAssertions.Companion.hasMoreThan
import com.test.fvba.testapp.utils.mockCategoryResponseSuccesful
import com.test.fvba.testapp.utils.mockImageResponseNoResult
import com.test.fvba.testapp.utils.mockImageResponseSuccessful
import io.reactivex.Flowable
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mockito.`when`
import retrofit2.Response
import javax.inject.Inject


@RunWith(AndroidJUnit4::class)
class MainActivityTest : TestApplicationTestRunner() {


    private var instrumentation: Instrumentation? = null

    private lateinit var testAppComponent: ApplicationTestComponent

    @Inject
    lateinit var apiService: ApiService

    @Rule
    @JvmField
    var mainActivity = ActivityTestRule(MainActivity::class.java, true, false)

    companion object {
        private const val SEARCH_TEXT_NO_RESULT = "dfsfsdfsdf"
    }

    @Before
    fun setUp() {
        instrumentation = InstrumentationRegistry.getInstrumentation()
        val app = InstrumentationRegistry.getTargetContext().applicationContext as TestApplication
        testAppComponent = DaggerApplicationTestComponent.builder()
                .applicationModule(ApplicationModule(app))
                .aPIModule(APITestModule(Constants.BASE_URL))
                .build()
        app.applicationComponent = testAppComponent
        testAppComponent.inject(this)
    }

    @Test
    fun shouldBeAbleToLoadData() {

        val fakeListCategory = mockCategoryResponseSuccesful()
        val fakeListImage = mockImageResponseSuccessful()

        `when`<Flowable<Response<ImagesResponse>>>(apiService.getImages(anyString())).thenReturn(Flowable.just(Response.success(fakeListImage)))
        `when`<Flowable<Response<CategoriesResponse>>>(apiService.getCategories()).thenReturn(Flowable.just(Response.success(fakeListCategory)))

        mainActivity.launchActivity(Intent())

        onView(withId(rv_list)).check(hasMoreThan(0))
        mainActivity.activity.finish()
    }

    @Test
    fun shouldBeAbleToSearch() {
        val fakeListCategory = mockCategoryResponseSuccesful()

        val fakeListImage = mockImageResponseNoResult()

        `when`<Flowable<Response<CategoriesResponse>>>(apiService.getCategories()).thenReturn(Flowable.just(Response.success(fakeListCategory)))

        `when`<Flowable<Response<ImagesResponse>>>(apiService.getImages(anyString())).thenReturn(Flowable.just(Response.success(fakeListImage)))

        mainActivity.launchActivity(Intent())

        onView(withId(R.id.search)).perform(click())
        onView(withId(android.support.design.R.id.search_src_text)).perform(typeText(SEARCH_TEXT_NO_RESULT), pressKey(KeyEvent.KEYCODE_ENTER))

        onView(withId(R.id.empty_screen_search)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)))

        mainActivity.activity.finish()
    }

}