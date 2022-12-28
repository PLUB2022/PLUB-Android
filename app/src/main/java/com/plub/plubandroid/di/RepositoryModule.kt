package com.plub.plubandroid.di

import com.plub.data.repository.IntroRepositoryImpl
import com.plub.data.repository.LoginRepositoryImpl
import com.plub.data.repository.SignUpRepositoryImpl
import com.plub.domain.repository.IntroRepository
import com.plub.domain.repository.LoginRepository
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
    abstract fun providesIntroRepository(repositoryImpl: IntroRepositoryImpl): IntroRepository

    @Singleton
    @Binds
    abstract fun providesSignUpRepository(repositoryImpl: SignUpRepositoryImpl): SignUpRepository
}