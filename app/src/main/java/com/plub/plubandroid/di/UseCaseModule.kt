package com.plub.plubandroid.di

import com.plub.domain.repository.*
import com.plub.domain.usecase.*
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

    @Singleton
    @Provides
    fun providesRecommendationGatheringUseCase(repository: RecommendationGatheringRepository): RecommendationGatheringUsecase {
        return RecommendationGatheringUsecase(repository)
    }

    @Singleton
    @Provides
    fun providesRecruitDetailUseCase(repository: RecruitDetailRepository): RecruitDetailUseCase {
        return RecruitDetailUseCase(repository)
    }
}