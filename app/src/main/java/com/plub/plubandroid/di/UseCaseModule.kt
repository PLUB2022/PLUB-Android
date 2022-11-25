package com.plub.plubandroid.di

import com.plub.domain.repository.IntroRepository
import com.plub.domain.repository.LoginRepository
import com.plub.domain.usecase.PostSocialLoginUseCase
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
    fun providesPostSocialLoginUseCase(repository: LoginRepository): PostSocialLoginUseCase {
        return PostSocialLoginUseCase(repository)
    }

    @Singleton
    @Provides
    fun providesTrySampleLoginUseCase(repository: IntroRepository): TrySampleLoginUseCase {
        return TrySampleLoginUseCase(repository)
    }
}