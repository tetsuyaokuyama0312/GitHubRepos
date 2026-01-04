package com.example.githubrepos.data.repository.pading

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.githubrepos.core.api.GitHubApiService
import com.example.githubrepos.core.api.extension.toRepoData
import com.example.githubrepos.core.model.RepoData

class RepoSearchPagingSource(
    private val apiService: GitHubApiService,
    private val query: String
) : PagingSource<Int, RepoData>() {

    override suspend fun load(
        params: LoadParams<Int>
    ): LoadResult<Int, RepoData> {
        val page = params.key ?: 1

        return try {
            val response = apiService.searchRepositories(
                query = query,
                page = page,
                perPage = params.loadSize
            )

            LoadResult.Page(
                data = response.items.map { it.toRepoData() },
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (response.items.isEmpty()) null else page + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(
        state: PagingState<Int, RepoData>
    ): Int? = null
}
