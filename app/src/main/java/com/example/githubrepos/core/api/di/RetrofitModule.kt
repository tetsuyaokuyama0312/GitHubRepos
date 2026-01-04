package com.example.githubrepos.core.api.di

import com.example.githubrepos.core.api.interceptor.GitHubAuthInterceptor
import com.example.githubrepos.core.common.AppConfig
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.Call
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {

    @Provides
    @Singleton
    fun providesNetworkJson(): Json = Json {
        ignoreUnknownKeys = true
        coerceInputValues = true
        encodeDefaults = true
        explicitNulls = false
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(
        appConfig: AppConfig
    ): Call.Factory {
        return OkHttpClient.Builder()
            .apply {
                if (appConfig.isDebug) {
                    addInterceptor(HttpLoggingInterceptor().apply {
                        level = HttpLoggingInterceptor.Level.BODY
                    })
                }
                addInterceptor(
                    GitHubAuthInterceptor {
                        appConfig.githubToken
                    }
                )

                connectTimeout(30, TimeUnit.SECONDS)
                readTimeout(30, TimeUnit.SECONDS)
                writeTimeout(30, TimeUnit.SECONDS)
            }
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(
        networkJson: Json,
        okhttpCallFactory: Call.Factory,
        appConfig: AppConfig,
    ): Retrofit = Retrofit.Builder()
        .baseUrl(appConfig.apiBaseUrl)
        .callFactory { okhttpCallFactory.newCall(it) }
        .addConverterFactory(networkJson.asConverterFactory("application/json".toMediaType()))
        .build()
}
