package com.example.githubrepos.core.common

interface AppConfig {
    val isDebug: Boolean
    val apiBaseUrl: String
    val githubToken: String
}
