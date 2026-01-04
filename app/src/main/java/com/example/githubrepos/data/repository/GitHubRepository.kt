package com.example.githubrepos.data.repository

import com.example.githubrepos.core.model.RepoData

interface GitHubRepository {

    suspend fun searchRepos(query: String): Result<List<RepoData>>

    suspend fun getRepoDetails(owner: String, name: String): Result<RepoData>
}
