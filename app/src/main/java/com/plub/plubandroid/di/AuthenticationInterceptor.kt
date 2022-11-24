package com.plub.plubandroid.di

import com.plub.domain.repository.PlubJwtTokenRepository
import com.plub.plubandroid.util.RETROFIT_TAG
import kotlinx.coroutines.*
import okhttp3.Interceptor
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthenticationInterceptor@Inject constructor(private val plubJwtTokenRepository: PlubJwtTokenRepository) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
        val accessToken = runBlocking { plubJwtTokenRepository.getAccessToken() }

        val request = chain.request().newBuilder()
            .addHeader("Authorization", "Bearer $accessToken").build()

        Timber.tag(RETROFIT_TAG).d(
            "AuthenticationInterceptor - intercept() called / request header: ${request.headers}"
        )
        return chain.proceed(request)
    }
}