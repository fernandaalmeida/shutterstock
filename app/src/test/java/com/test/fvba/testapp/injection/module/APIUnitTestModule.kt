package com.test.fvba.testapp.injection.module

import com.test.fvba.testapp.data.api.ApiService
import org.mockito.Mockito.mock
import retrofit2.Retrofit

class APIUnitTestModule(baseUrl:String) : APIModule(baseUrl) {

    override fun provideApiService(retrofit: Retrofit): ApiService {
        return mock<ApiService>(ApiService::class.java)
    }

}