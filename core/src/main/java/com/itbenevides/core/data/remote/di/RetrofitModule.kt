package com.itbenevides.core.data.remote.di

import com.itbenevides.core.data.remote.APIService
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {

    @Provides
    fun provideBaseUrl(): String = "https://api.github.com/"

    @Provides
    @Singleton
    fun provideRetrofit(baseUrl: String): Retrofit {

        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

        val client: OkHttpClient = OkHttpClient.Builder().addInterceptor { chain ->
            val newRequest: Request = chain.request().newBuilder()
                .addHeader(
                    "Authorization",
                    "Bearer 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"
                )
                .build()
            chain.proceed(newRequest)
        }.build()
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build()
    }

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): APIService = retrofit.create(APIService::class.java)

}
