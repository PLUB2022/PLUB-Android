package com.plub.plubandroid.di

import com.plub.data.api.*
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
    fun provideBrwoseApi(@AuthRetrofit retrofit: Retrofit): BrowseApi {
        return retrofit.create(BrowseApi::class.java)
    }

    @Singleton
    @Provides
    fun provideHobbyApi(@NormalRetrofit retrofit: Retrofit): HobbyApi {
        return retrofit.create(HobbyApi::class.java)
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

    @Singleton
    @Provides
    fun provideMediaApi(@NormalRetrofit retrofit: Retrofit): MediaApi {
        return retrofit.create(MediaApi::class.java)
    }

    @Singleton
    @Provides
    fun providePostApi(@NormalRetrofit retrofit: Retrofit): PostHomeApi {
        return retrofit.create(PostHomeApi::class.java)
    }

    @Singleton
    @Provides
    fun provideRecruiApi(@AuthRetrofit retrofit: Retrofit): RecruitApi {
        return retrofit.create(RecruitApi::class.java)
    }

    @Singleton
    @Provides
    fun provideInterestApi(@AuthRetrofit retrofit: Retrofit): InterestApi {
        return retrofit.create(InterestApi::class.java)
    }
}