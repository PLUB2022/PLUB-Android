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
    abstract fun providesHomePostRepository(homePostReposImpl: HomePostReposImpl): HomePostRepository

    @Singleton
    @Binds
    abstract fun providesCategoriesGatheringRepository(categoriesGatheringResposImpl: CategoriesGatheringReposImpl): CategoriesGatheringRepository

    @Singleton
    @Binds
    abstract fun providesCategoryListRepository(categoryListResposImpl: CategoryListRespoImpl): CategoryListRepository

    @Singleton
    @Binds
    abstract fun providesRecommendationGatheringRepository(recommendationGatheringResposImpl: RecommendationGatheringReposImpl): RecommendationGatheringRepository

    @Singleton
    @Binds
    abstract fun providesRecruitDetailRepository(recruitDetailResposImpl: RecruitDetailReposImpl): RecruitDetailRepository

    @Singleton
    @Binds
    abstract fun providesLoginRepository(repositoryImpl: LoginRepositoryImpl): LoginRepository

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
    abstract fun providesApplicantsRecruitRepository(repositoryImpl: ApplicantsRecruitReposImpl): ApplicantsRecruitRepository

    @Singleton
    @Binds
    abstract fun providesReplyApplicantsRepository(repositoryImpl: ReplyApplicantsRecruitReposImpl): ReplyApplicantsRecruitRepository

    @Singleton
    @Binds
    abstract fun providesBookmarkRepository(repositoryImpl: BookmarkReposImpl): BookmarkRepository
}