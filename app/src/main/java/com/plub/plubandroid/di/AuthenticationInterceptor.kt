package com.plub.plubandroid.di

import okhttp3.Interceptor
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthenticationInterceptor@Inject constructor() : Interceptor {
    override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
//        val accessToken = FinpoApplication.encryptedPrefs.getAccessToken() ?: ""
        val request = chain.request().newBuilder()
            .addHeader("Authorization", "Bearer accessToken").build()
//        Log.d(
//            RETROFIT_TAG,
//            "AuthenticationInterceptor - intercept() called / request header: ${request.headers}"
//        )
        return chain.proceed(request)
    }
}