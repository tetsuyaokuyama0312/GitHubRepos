package com.example.githubrepos.core.api

import com.example.githubrepos.core.api.model.GitHubRepo
import com.example.githubrepos.core.api.model.RepoSearchResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GitHubApiService {
    @GET("search/repositories")
    suspend fun searchRepositories(
        @Query("q") query: String,
        @Query("sort") sort: String = "stars"
    ): RepoSearchResponse

    @GET("repos/{owner}/{name}")
    suspend fun getRepositoryDetails(
        @Path("owner") owner: String,
        @Path("name") name: String
    ): GitHubRepo
}
