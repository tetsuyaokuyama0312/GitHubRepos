package com.example.githubrepos.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.githubrepos.core.model.RepoData
import com.example.githubrepos.data.api.GitHubApiService
import com.example.githubrepos.data.repository.pading.RepoSearchPagingSource
import kotlinx.coroutines.flow.Flow

class GitHubRepositoryImpl(private val apiService: GitHubApiService) : GitHubRepository {

    // リポジトリを検索する
    override suspend fun searchRepos(query: String): Flow<PagingData<RepoData>> {
        return Pager(
            config = PagingConfig(
                pageSize = 30,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                RepoSearchPagingSource(apiService, query)
            }
        ).flow
    }
}
