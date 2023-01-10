package com.plub.plubandroid.di

import com.plub.domain.repository.CategoryListRepository
import com.plub.domain.repository.HomePostRepository
import com.plub.domain.repository.IntroRepository
import com.plub.domain.usecase.BrowseUseCase
import com.plub.domain.usecase.TestPostHomeUseCase
import com.plub.domain.usecase.TrySampleLoginUseCase
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Singleton
    @Provides
    fun providesTrySampleLoginUseCase(repository: IntroRepository): TrySampleLoginUseCase {
        return TrySampleLoginUseCase(repository)
    }

    @Singleton
    @Provides
    fun providesTestPostHomeUseCase(repository: HomePostRepository): TestPostHomeUseCase {
        return TestPostHomeUseCase(repository)
    }

    @Singleton
    @Provides
    fun providesBrowseUseCase(repository: CategoryListRepository): BrowseUseCase {
        return BrowseUseCase(repository)
    }
}