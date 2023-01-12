package com.plub.plubandroid.di

import com.plub.data.repository.*
import com.plub.domain.repository.*
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

    @Singleton
    @Binds
    abstract fun providesCategoryListRepository(categoryListResposImpl: CategoryListResposImpl): CategoryListRepository

    @Singleton
    @Binds
    abstract fun providesRecommendationGatheringRepository(recommendationGatheringResposImpl: RecommendationGatheringResposImpl): RecommendationGatheringRepository

    @Singleton
    @Binds
    abstract fun providesRecruitDetailRepository(recruitDetailResposImpl: RecruitDetailResposImpl): RecruitDetailRepository
}