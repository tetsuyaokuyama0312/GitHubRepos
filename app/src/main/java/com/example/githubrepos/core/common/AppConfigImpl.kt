package com.example.githubrepos.core.common

import com.example.githubrepos.BuildConfig

object AppConfigImpl : AppConfig {
    override val isDebug: Boolean
        get() = BuildConfig.DEBUG

    override val apiBaseUrl: String
        get() = BuildConfig.API_BASE_URL

    override val githubToken: String
        get() = BuildConfig.GITHUB_TOKEN
}
