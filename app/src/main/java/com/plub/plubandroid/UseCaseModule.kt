package com.plub.plubandroid

import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import repository.UserRepository
import usecase.GetUserUseCase
import usecase.LoginUseCase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class UseCaseModule {

    @Provides
    fun provideGetUserUseCase(repository: UserRepository) : GetUserUseCase{
        return GetUserUseCase(repository)
    }

    @Provides
    fun provideLoginUseCase(repository: UserRepository) : LoginUseCase{
        return LoginUseCase(repository)
    }
}