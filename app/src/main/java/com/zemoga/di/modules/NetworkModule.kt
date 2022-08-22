package com.zemoga.di.modules

import com.zemoga.BuildConfig
import com.zemoga.features.posts.data.datasources.remote.PostsApi
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
object NetworkModule {

    private const val URL: String = "https://jsonplaceholder.typicode.com/"
    private const val DEFAULT_TIME_OUT: Long = 3000

    @Provides
    @Singleton
    fun getOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder().apply {
            readTimeout(DEFAULT_TIME_OUT, TimeUnit.MILLISECONDS)
            connectTimeout(DEFAULT_TIME_OUT, TimeUnit.MILLISECONDS)
            val httpLoggingInterceptor =
                HttpLoggingInterceptor(HttpLoggingInterceptor.Logger.DEFAULT)
            if (BuildConfig.DEBUG) {
                httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
                addInterceptor(httpLoggingInterceptor)
            }
        }.build()
    }

    @Provides
    @Singleton
    fun getServicesClient(httpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .client(httpClient)
            .baseUrl(URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun providePostsApi(retrofit: Retrofit): PostsApi {
        return retrofit.create(PostsApi::class.java)
    }
}