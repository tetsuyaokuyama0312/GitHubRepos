package com.example.githubrepos.core.model

data class RepoData(
    val id: Long,
    val name: String,
    val description: String?,
    val stargazersCount: Int,
    val language: String?,
    val htmlUrl: String,
)
