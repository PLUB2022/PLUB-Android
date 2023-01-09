package com.plub.plubandroid.di

import com.plub.domain.repository.HomePostRepository
import com.plub.domain.repository.KakaoLocationRepository
import com.plub.domain.repository.LoginRepository
import com.plub.domain.repository.PlubJwtRepository
import com.plub.domain.repository.*
import com.plub.domain.usecase.*
import com.plub.domain.usecase.TestPostHomeUseCase
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
    fun providesPostSocialLoginUseCase(repository: LoginRepository): PostSocialLoginUseCase {
        return PostSocialLoginUseCase(repository)
    }

    @Singleton
    @Provides
    fun providesFetchPlubAccessTokenUseCase(repository: PlubJwtRepository): FetchPlubAccessTokenUseCase {
        return FetchPlubAccessTokenUseCase(repository)
    }

    @Singleton
    @Provides
    fun providesFetchPlubRefreshTokenUseCase(repository: PlubJwtRepository): FetchPlubRefreshTokenUseCase {
        return FetchPlubRefreshTokenUseCase(repository)
    }

    @Singleton
    @Provides
    fun providesSavePlubAccessTokenAndRefreshTokenUseCase(repository: PlubJwtRepository): SavePlubAccessTokenAndRefreshTokenUseCase {
        return SavePlubAccessTokenAndRefreshTokenUseCase(repository)
    }

    @Singleton
    @Provides
    fun providesPostReIssueTokenUseCase(repository: PlubJwtRepository): PostReIssueTokenUseCase {
        return PostReIssueTokenUseCase(repository)
    }

    @Singleton
    @Provides
    fun providesGetNicknameCheckUseCase(repository: SignUpRepository): GetNicknameCheckUseCase {
        return GetNicknameCheckUseCase(repository)
    }

    @Singleton
    @Provides
    fun providesGetAllHobbiesUseCase(repository: HobbyRepository): GetAllHobbiesUseCase {
        return GetAllHobbiesUseCase(repository)
    }

    @Singleton
    @Provides
    fun providesPostSignUpUseCase(repository: SignUpRepository): PostSignUpUseCase {
        return PostSignUpUseCase(repository)
    }

    @Singleton
    @Provides
    fun providesUploadFileUseCase(repository: MediaRepository): PostUploadFileUseCase {
        return PostUploadFileUseCase(repository)
    }

    @Singleton
    @Provides
    fun providesTestPostHomeUseCase(repository: HomePostRepository): TestPostHomeUseCase {
        return TestPostHomeUseCase(repository)
    }

    @Singleton
    @Provides
    fun providesFetchKakaoLocationByKeywordUseCase(repository: KakaoLocationRepository) : FetchKakaoLocationByKeywordUseCase {
        return FetchKakaoLocationByKeywordUseCase(repository)
    }
}