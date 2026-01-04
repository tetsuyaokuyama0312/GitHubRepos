package com.example.githubrepos.core.common

interface BuildConfigProvider {
    val isDebug: Boolean
    val apiBaseUrl: String
}
