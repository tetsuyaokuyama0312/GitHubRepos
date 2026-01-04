package com.example.githubrepos.core.model

data class RepoData(
    val id: Long,
    val name: String,
    val fullName: String,
    val description: String?,
    val homepage: String?,
    val stargazersCount: Int,
    val language: String?,
    val htmlUrl: String,
    val owner: RepoOwnerData
)

data class RepoOwnerData(
    val login: String,
    val avatarUrl: String
)
