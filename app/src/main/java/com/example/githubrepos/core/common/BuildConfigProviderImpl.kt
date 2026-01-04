package com.example.githubrepos.core.common

import com.example.githubrepos.BuildConfig

object BuildConfigProviderImpl : BuildConfigProvider {
    override val isDebug: Boolean
        get() = BuildConfig.DEBUG

    override val apiBaseUrl: String
        get() = "https://api.github.com/"
}
