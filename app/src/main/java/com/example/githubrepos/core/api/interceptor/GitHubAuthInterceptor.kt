package com.example.githubrepos.core.api.interceptor

import okhttp3.Interceptor
import okhttp3.Response

class GitHubAuthInterceptor(
    private val tokenProvider: () -> String
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
            .newBuilder()
            .addHeader(
                "Authorization",
                "Bearer ${tokenProvider()}"
            )
            .build()

        return chain.proceed(request)
    }
}
