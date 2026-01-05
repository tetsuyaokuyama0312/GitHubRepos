package com.example.githubrepos.data.repository.di

import com.example.githubrepos.data.api.GitHubApiService
import com.example.githubrepos.data.repository.GitHubRepository
import com.example.githubrepos.data.repository.GitHubRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun providesGitHubRepository(apiService: GitHubApiService): GitHubRepository {
        return GitHubRepositoryImpl(apiService)
    }
}