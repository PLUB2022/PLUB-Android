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
    abstract fun providesLoginRepository(repositoryImpl: LoginRepositoryImpl): LoginRepository

    @Singleton
    @Binds
    abstract fun providesKakaoLocationRepository(repositoryImpl: KakaoLocationRepositoryImpl): KakaoLocationRepository

    @Singleton
    @Binds
    abstract fun providesHobbyRepository(repositoryImpl: HobbyRepositoryImpl): HobbyRepository

    @Singleton
    @Binds
    abstract fun providesSignUpRepository(repositoryImpl: SignUpRepositoryImpl): SignUpRepository

    @Singleton
    @Binds
    abstract fun providesMediaRepository(repositoryImpl: MediaRepositoryImpl): MediaRepository

    @Singleton
    @Binds
    abstract fun providesGatheringRepository(repositoryImpl: GatheringRepositoryImpl): GatheringRepository

    @Singleton
    @Binds
    abstract fun providesAccountRepository(repositoryImpl: AccountRepositoryImpl): AccountRepository

    @Singleton
    @Binds
    abstract fun providesSearchRepository(repositoryImpl: SearchRepositoryImpl): SearchRepository

    @Singleton
    @Binds
    abstract fun providesBookmarkRepository(repositoryImpl: BookmarkRepositoryImpl): BookmarkRepository

    @Singleton
    @Binds
    abstract fun providesCategoryListRepository(categoryListResposImpl: CategoryListRepositoryImpl): CategoryListRepository

    @Singleton
    @Binds
    abstract fun providesRecommendationGatheringRepository(recommendationGatheringResposImpl: RecommendationGatheringRepositoryImpl): RecommendationGatheringRepository

    @Singleton
    @Binds
    abstract fun providesRecruitDetailRepository(recruitDetailResposImpl: RecruitDetailRepositoryImpl): RecruitRepository

    @Singleton
    @Binds
    abstract fun providesReplyApplicantsRepository(repositoryImpl: ReplyApplicantsRecruitRepositoryImpl): ReplyApplicantsRecruitRepository

    @Singleton
    @Binds
    abstract fun providesInterestRepository(repositoryImpl: RegistInterestRepositoryImpl): RegisterInterestRepository

    @Singleton
    @Binds
    abstract fun providesApplicantsRepository(repositoryImpl: ApplicantsRepositoryImpl): ApplicantsRepository
}