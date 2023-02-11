package com.plub.plubandroid.di

import com.plub.data.api.KakaoLocationApi
import com.plub.data.api.LoginApi
import com.plub.data.api.PlubJwtTokenApi
import com.plub.domain.repository.LoginRepository
import com.plub.domain.usecase.PostSocialLoginUseCase
import dagger.Binds
import com.plub.data.api.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

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
    fun provideKakaoLocationApi(@KakaoLocationRetrofit retrofit: Retrofit): KakaoLocationApi {
        return retrofit.create(KakaoLocationApi::class.java)
    }

    @Singleton
    @Provides
    fun provideMediaApi(@NormalRetrofit retrofit: Retrofit): MediaApi {
        return retrofit.create(MediaApi::class.java)
    }

    @Singleton
    @Provides
    fun provideGatheringApi(@AuthRetrofit retrofit: Retrofit): GatheringApi {
        return retrofit.create(GatheringApi::class.java)
    }

    @Singleton
    @Provides
    fun provideAccountApi(@AuthRetrofit retrofit: Retrofit): AccountApi {
        return retrofit.create(AccountApi::class.java)
    }

    @Singleton
    @Provides
    fun provideSearchApi(@AuthRetrofit retrofit: Retrofit): SearchApi {
        return retrofit.create(SearchApi::class.java)
    }

    @Singleton
    @Provides
    fun provideBookmarkApi(@AuthRetrofit retrofit: Retrofit): BookmarkApi {
        return retrofit.create(BookmarkApi::class.java)
    }

    @Singleton
    @Provides
    fun provideArchiveApi(@AuthRetrofit retrofit: Retrofit): ArchiveApi {
        return retrofit.create(ArchiveApi::class.java)
    }
}