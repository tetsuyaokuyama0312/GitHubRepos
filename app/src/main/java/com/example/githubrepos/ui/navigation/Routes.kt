package com.example.githubrepos.ui.navigation

import kotlinx.serialization.Serializable

@Serializable
object Routes {
    @Serializable
    data object Search

    @Serializable
    data object Details
}
