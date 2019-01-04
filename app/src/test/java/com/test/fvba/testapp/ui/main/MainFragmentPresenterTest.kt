package com.test.fvba.testapp.ui.main

import com.test.fvba.testapp.ApplicationUnitTestComponent
import com.test.fvba.testapp.DaggerApplicationUnitTestComponent
import com.test.fvba.testapp.data.api.ApiService
import com.test.fvba.testapp.data.model.CategoriesResponse
import com.test.fvba.testapp.injection.module.APIUnitTestModule
import com.test.fvba.testapp.util.Constants
import io.reactivex.Flowable
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import retrofit2.Response
import java.net.UnknownHostException
import javax.inject.Inject
import org.mockito.Mockito.`when` as whenever


class MainFragmentPresenterTest {

    @Inject
    lateinit var apiService: ApiService

    private lateinit var testAppComponent: ApplicationUnitTestComponent

    @Mock
    lateinit var mockMainFragmentView: MainFragmentView
    @Inject
    lateinit var mainFragmentPresenter: MainFragmentPresenter<MainFragmentView>

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        testAppComponent = DaggerApplicationUnitTestComponent.builder()
                .aPIModule(APIUnitTestModule(Constants.BASE_URL))
                .build()
        testAppComponent.inject(this)
        testAppComponent.inject(mainFragmentPresenter)
        RxJavaPlugins.setIoSchedulerHandler { Schedulers.trampoline() }
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline() }

    }

    @Test
    fun `should show connection error`() {
        mainFragmentPresenter.attachView(mockMainFragmentView)

        whenever<Flowable<Response<CategoriesResponse>>>(apiService.getCategories()).thenReturn(Flowable.error(UnknownHostException()))

        mainFragmentPresenter.requestImages( )

        verify(mockMainFragmentView).onNoConnection()
    }

}