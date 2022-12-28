package com.plub.plubandroid.di

import com.plub.data.api.IntroApi
import com.plub.data.api.LoginApi
import com.plub.data.api.PlubJwtTokenApi
import com.plub.data.api.SignUpApi
import com.plub.domain.repository.IntroRepository
import com.plub.domain.repository.LoginRepository
import com.plub.domain.usecase.PostSocialLoginUseCase
import com.plub.domain.usecase.TrySampleLoginUseCase
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    @Singleton
    @Provides
    fun provideIntroApi(@NormalRetrofit retrofit: Retrofit): IntroApi {
        return retrofit.create(IntroApi::class.java)
    }

    @Singleton
    @Provides
    fun provideLoginApi(@NormalRetrofit retrofit: Retrofit): LoginApi {
        return retrofit.create(LoginApi::class.java)
    }

    @Singleton
    @Provides
    fun provideSignUpApi(@NormalRetrofit retrofit: Retrofit): SignUpApi {
        return retrofit.create(SignUpApi::class.java)
    }

    @Singleton
    @Provides
    fun providePlubJwtTokenApi(@NormalRetrofit retrofit: Retrofit): PlubJwtTokenApi {
        return retrofit.create(PlubJwtTokenApi::class.java)
    }
}