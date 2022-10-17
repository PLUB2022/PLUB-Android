package com.plub.plubandroid.di

import com.google.gson.GsonBuilder
import com.google.gson.JsonParser
import com.google.gson.JsonSyntaxException
import com.plub.data.api.IntroApi
import com.plub.data.api.PlubJwtTokenApi
import com.plub.data.api.SampleApi
import com.plub.plubandroid.util.BASE_URL
import com.plub.plubandroid.util.RETROFIT_TAG
import com.plub.plubandroid.util.isJsonArray
import com.plub.plubandroid.util.isJsonObject
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NormalNetworkModule {
    @Provides
    @Singleton
    @NormalOkHttpClient
    fun provideHttpClient(
        loggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .readTimeout(10, TimeUnit.SECONDS)
            .connectTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(15, TimeUnit.SECONDS)
            .addInterceptor(loggingInterceptor)
            .build()
    }

    @Provides
    @Singleton
    fun provideConverterFactory(): GsonConverterFactory {
        return GsonConverterFactory.create()
    }

    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        val loggingInterceptor = HttpLoggingInterceptor { message ->
            when {
                !message.isJsonObject() && !message.isJsonArray() ->
                    Timber.tag(RETROFIT_TAG).d("CONNECTION INFO -> $message")
                else ->  try {
                    Timber.tag(RETROFIT_TAG).d(GsonBuilder().setPrettyPrinting().create().toJson(
                        JsonParser().parse(message)))
                } catch (m: JsonSyntaxException) {
                    Timber.tag(RETROFIT_TAG).d(message)
                }
            }
        }
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return loggingInterceptor
    }

    @Singleton
    @Provides
    @NormalRetrofit
    fun provideNormalRetrofit(
        @NormalOkHttpClient okHttpClient: OkHttpClient,
        gsonConverterFactory: GsonConverterFactory
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(gsonConverterFactory)
            .build()
    }

    @Singleton
    @Provides
    fun providePlubJwtTokenApi(@NormalRetrofit retrofit: Retrofit): PlubJwtTokenApi {
        return retrofit.create(PlubJwtTokenApi::class.java)
    }

    @Singleton
    @Provides
    fun provideIntroApi(@NormalRetrofit retrofit: Retrofit): IntroApi {
        return retrofit.create(IntroApi::class.java)
    }
}