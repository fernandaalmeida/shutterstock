package com.test.fvba.testapp.injection.module

import com.google.gson.GsonBuilder
import com.test.fvba.testapp.data.api.ApiService
import com.test.fvba.testapp.data.api.AuthenticationInterceptor
import com.test.fvba.testapp.util.Constants
import dagger.Module
import dagger.Provides
import okhttp3.Credentials
import okhttp3.OkHttpClient
import okhttp3.OkHttpClient.Builder
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
open class APIModule(private var mBaseUrl: String) {

    companion object {

        const val DEFAULT_TIMEOUT_SECONDS: Long = 60
        const val ONE_MB_BYTES: Long = 1024

    }

    @Provides
    @Singleton
    open fun provideApiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
                .baseUrl(mBaseUrl)
                .client(okHttpClient)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(provideGson())
                .build()
    }

    @Provides
    @Singleton
    fun provideGson(): GsonConverterFactory {
        val gson = GsonBuilder()
                .setLenient()
                .serializeNulls()
                .create()
        return GsonConverterFactory.create(gson)
    }

//    @Provides
//    @Singleton
//    fun provideCache(context: Context): Cache {
//        //5mb cache
//        val cacheSize = (5 * ONE_MB_BYTES * ONE_MB_BYTES).toLong()
//        return Cache(context.cacheDir, cacheSize)
//    }

    @Provides
    @Singleton
    fun provideOkhttpClient(): OkHttpClient {
        val authToken = Credentials.basic(Constants.API_KEY, Constants.API_SECRET)
        val authInterceptor = AuthenticationInterceptor(authToken!!)

        val builder = Builder()
        builder.apply {
            connectTimeout(DEFAULT_TIMEOUT_SECONDS, TimeUnit.SECONDS)
            readTimeout(DEFAULT_TIMEOUT_SECONDS, TimeUnit.SECONDS)
            writeTimeout(DEFAULT_TIMEOUT_SECONDS, TimeUnit.SECONDS)
//            cache(cache)
//            addInterceptor(cacheInterceptor)
            addInterceptor(authInterceptor)
        }

        return builder.build()
    }


}