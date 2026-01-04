package com.example.githubrepos.core.common.di

import com.example.githubrepos.core.common.BuildConfigProvider
import com.example.githubrepos.core.common.BuildConfigProviderImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CommonModule {

    @Provides
    @Singleton
    fun providesBuildConfigProvider(): BuildConfigProvider {
        return BuildConfigProviderImpl
    }
}
