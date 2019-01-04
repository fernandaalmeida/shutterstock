package com.test.fvba.testapp.ui.search

import com.test.fvba.testapp.ApplicationUnitTestComponent
import com.test.fvba.testapp.DaggerApplicationUnitTestComponent
import com.test.fvba.testapp.data.api.ApiService
import com.test.fvba.testapp.data.model.ImagesResponse
import com.test.fvba.testapp.injection.module.APIUnitTestModule
import com.test.fvba.testapp.util.Constants
import io.reactivex.Flowable
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.anyString
import org.mockito.MockitoAnnotations
import retrofit2.Response
import org.mockito.Mockito.`when` as whenever
import javax.inject.Inject

class SearchFragmentPresenterTest {
    @Inject
    lateinit var apiService: ApiService

    private lateinit var testAppComponent: ApplicationUnitTestComponent

    @Mock
    lateinit var mockSearchFragmentView: SearchFragmentView
    @Inject
    lateinit var searchFragmentPresenter: SearchFragmentPresenter<SearchFragmentView>

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        testAppComponent = DaggerApplicationUnitTestComponent.builder()
                .aPIModule(APIUnitTestModule(Constants.BASE_URL))
                .build()
        testAppComponent.inject(this)
        testAppComponent.inject(searchFragmentPresenter)
        RxJavaPlugins.setIoSchedulerHandler { Schedulers.trampoline() }
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline() }

    }

    @Test
    fun `should show message informing that no results were found`() {
        searchFragmentPresenter.attachView(mockSearchFragmentView)

        whenever<Flowable<Response<ImagesResponse>>>(apiService.getImages(anyString())).thenReturn(Flowable.just(Response.success(ImagesResponse(1, ArrayList()))))

        searchFragmentPresenter.requestImages( anyString())

        Mockito.verify(mockSearchFragmentView).onNoResultsFound()
    }
}