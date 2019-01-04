package com.test.fvba.testapp.module

import com.test.fvba.testapp.data.api.ApiService
import com.test.fvba.testapp.injection.module.APIModule
import org.mockito.Mockito.mock
import retrofit2.Retrofit

class APITestModule(baseUrl:String) : APIModule(baseUrl) {

    override fun provideApiService(retrofit: Retrofit): ApiService {
        return mock<ApiService>(ApiService::class.java)
    }
}