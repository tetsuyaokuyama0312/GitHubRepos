package com.example.githubrepos.data.repository

import androidx.paging.PagingData
import com.example.githubrepos.core.model.RepoData
import kotlinx.coroutines.flow.Flow

interface GitHubRepository {

    suspend fun searchRepos(query: String): Flow<PagingData<RepoData>>

    suspend fun getRepoDetails(owner: String, name: String): Result<RepoData>
}
