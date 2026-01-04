package com.example.githubrepos.data.repository

import com.example.githubrepos.core.api.GitHubApiService
import com.example.githubrepos.core.api.extension.toRepoData
import com.example.githubrepos.core.model.RepoData

class GitHubRepositoryImpl(private val apiService: GitHubApiService) : GitHubRepository {

    // リポジトリを検索する
    override suspend fun searchRepos(query: String): Result<List<RepoData>> {
        return try {
            val response = apiService.searchRepositories(query)
            val items = response.items.map { it.toRepoData() }
            Result.success(items)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // 特定のリポジトリ詳細を取得する
    override suspend fun getRepoDetails(owner: String, name: String): Result<RepoData> {
        return try {
            val repo = apiService.getRepositoryDetails(owner, name)
            Result.success(repo.toRepoData())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
