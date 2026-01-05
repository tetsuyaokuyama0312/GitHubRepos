package com.example.githubrepos.core.common.di

import com.example.githubrepos.core.common.AppConfig
import com.example.githubrepos.core.common.AppConfigImpl
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
    fun providesBuildConfigProvider(): AppConfig {
        return AppConfigImpl
    }
}
