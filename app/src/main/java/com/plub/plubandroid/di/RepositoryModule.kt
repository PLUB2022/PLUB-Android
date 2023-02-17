package com.plub.plubandroid.di

import com.plub.data.repository.AccountRepositoryImpl
import com.plub.data.repository.BookmarkRepositoryImpl
import com.plub.data.repository.GatheringRepositoryImpl
import com.plub.data.repository.HomePostReposImpl
import com.plub.data.repository.KakaoLocationRepositoryImpl
import com.plub.data.repository.HobbyRepositoryImpl
import com.plub.data.repository.LoginRepositoryImpl
import com.plub.data.repository.MediaRepositoryImpl
import com.plub.data.repository.RecruitDetailRepositoryImpl
import com.plub.data.repository.SearchRepositoryImpl
import com.plub.data.repository.SignUpRepositoryImpl
import com.plub.domain.repository.AccountRepository
import com.plub.domain.repository.BookmarkRepository
import com.plub.domain.repository.GatheringRepository
import com.plub.domain.repository.HobbyRepository
import com.plub.domain.repository.HomePostRepository
import com.plub.domain.repository.KakaoLocationRepository
import com.plub.domain.repository.LoginRepository
import com.plub.domain.repository.MediaRepository
import com.plub.domain.repository.RecruitRepository
import com.plub.domain.repository.SearchRepository
import com.plub.domain.repository.SignUpRepository
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
    abstract fun providesHomePostRepository(homePostReposImpl: HomePostReposImpl): HomePostRepository

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
    abstract fun providesRecruitDetailRepository(recruitDetailResposImpl: RecruitDetailRepositoryImpl): RecruitRepository
}