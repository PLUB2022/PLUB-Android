package com.plub.plubandroid

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import source.UserRemoteSource
import source.UserRemoteSourceImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataSourceModule {

    @Singleton
    @Binds
    abstract fun bindsGithubRemoteSource(source: UserRemoteSourceImpl): UserRemoteSource
}