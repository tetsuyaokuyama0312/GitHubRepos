package com.example.githubrepos.data.api.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

// リポジトリの詳細情報
@Serializable
data class GitHubRepo(
    val id: Long,
    val name: String,
    val description: String?,
    @SerialName("stargazers_count")
    val stargazersCount: Int,
    val language: String?,
    @SerialName("html_url")
    val htmlUrl: String,
)
