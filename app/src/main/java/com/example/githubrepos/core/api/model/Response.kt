package com.example.githubrepos.core.api.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

// 検索結果のラッパー
@Serializable
data class RepoSearchResponse(
    val items: List<GitHubRepo>
)

// リポジトリの詳細情報
@Serializable
data class GitHubRepo(
    val id: Long,
    val name: String,
    @SerialName("full_name")
    val fullName: String,
    val description: String?,
    @SerialName("home_page")
    val homePage: String?,
    @SerialName("stargazers_count")
    val stargazersCount: Int,
    val language: String?,
    @SerialName("html_url")
    val htmlUrl: String,
    val owner: RepoOwner
)

@Serializable
data class RepoOwner(
    val login: String,
    @SerialName("avatar_url")
    val avatarUrl: String
)
