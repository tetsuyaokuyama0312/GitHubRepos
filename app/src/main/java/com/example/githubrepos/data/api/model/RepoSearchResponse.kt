package com.example.githubrepos.data.api.model

import kotlinx.serialization.Serializable

// 検索結果のラッパー
@Serializable
data class RepoSearchResponse(
    val items: List<GitHubRepo>
)
