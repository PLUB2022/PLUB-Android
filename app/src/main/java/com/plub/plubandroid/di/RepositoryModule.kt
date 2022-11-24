package com.plub.plubandroid.di

import com.plub.data.repository.HomePostReposImpl
import com.plub.data.repository.IntroRepositoryImpl
import com.plub.domain.repository.HomePostRepository
import com.plub.domain.repository.IntroRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Singleton
    @Binds
    abstract fun providesIntroRepository(repositoryImpl: IntroRepositoryImpl): IntroRepository

    @Singleton
    @Binds
    abstract fun providesHomePostRepository(homePostReposImpl: HomePostReposImpl): HomePostRepository
}