package com.plub.plubandroid.di

import android.util.Log
import kotlinx.coroutines.*
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Route
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TokenAuthenticator@Inject constructor() : Authenticator {
//    @Inject lateinit var api: ApiServiceWithoutToken

    override fun authenticate(route: Route?, response: okhttp3.Response): Request? {
        return null
//        val access = FinpoApplication.encryptedPrefs.getAccessToken() ?: ""
//        val refresh = FinpoApplication.encryptedPrefs.getRefreshToken() ?: ""
//
//        synchronized(this) {
//            val newAccess = FinpoApplication.encryptedPrefs.getAccessToken() ?: ""
//
//            val isTokenRefreshed = if(access != newAccess) true else {
//                Log.d(RETROFIT_TAG, "TokenAuthenticator - authenticate() called / 토큰 만료. 토큰 Refresh 요청: $refresh")
//                val tokenResponse = runBlocking { api.refreshToken(RequestTokenBody(access, refresh)) }
//                handleResponse(tokenResponse)
//            }
//
//            return if (isTokenRefreshed) {
//                Log.d(RETROFIT_TAG, "TokenAuthenticator - authenticate() called / 중단된 API 재요청")
//                response.request
//                    .newBuilder()
//                    .removeHeader("Authorization")
//                    .header(
//                        "Authorization",
//                        "Bearer " + FinpoApplication.encryptedPrefs.getAccessToken()
//                    )
//                    .build()
//            } else {
//                null
//            }
//        }

    }

//    private fun handleResponse(tokenResponse: ApiResponse<TokenResponse>) =
//        if (tokenResponse is ApiResponse.Success) {
//            FinpoApplication.encryptedPrefs.saveAccessToken(tokenResponse.data.data.accessToken ?: "")
//            FinpoApplication.encryptedPrefs.saveRefreshToken(tokenResponse.data.data.refreshToken ?: "")
//            true
//        } else {
//            Log.d(RETROFIT_TAG, "TokenAuthenticator - handleResponse() called / 리프레시 토큰이 만료되어 로그 아웃 되었습니다.")
//            false
//        }
}