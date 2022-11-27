package com.plub.plubandroid.di

import com.plub.domain.usecase.FetchPlubAccessTokenUseCase
import com.plub.plubandroid.util.RETROFIT_TAG
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.first
import okhttp3.Interceptor
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthenticationInterceptor@Inject constructor(private val fetchPlubAccessTokenUseCase: FetchPlubAccessTokenUseCase) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
        val accessToken = runBlocking { fetchPlubAccessTokenUseCase(Unit).first() }

        val request = chain.request().newBuilder()
            .addHeader("Authorization", "Bearer $accessToken").build()

        Timber.tag(RETROFIT_TAG).d(
            "AuthenticationInterceptor - intercept() called / request header: ${request.headers}"
        )
        return chain.proceed(request)
    }
}