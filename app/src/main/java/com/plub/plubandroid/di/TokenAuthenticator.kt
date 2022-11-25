package com.plub.plubandroid.di

import com.plub.data.api.PlubJwtTokenApi
import com.plub.data.model.JWTTokenReIssueRequest
import com.plub.data.model.PlubJwtTokenResponse
import com.plub.domain.model.vo.jwt_token.PlubJwtTokenData
import com.plub.domain.repository.PlubJwtTokenRepository
import com.plub.plubandroid.util.RETROFIT_TAG
import kotlinx.coroutines.*
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Route
import retrofit2.Response
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TokenAuthenticator @Inject constructor(
    private val plubJwtTokenRepository: PlubJwtTokenRepository
) : Authenticator {
    private val mutex = Mutex()

    override fun authenticate(route: Route?, response: okhttp3.Response): Request? = runBlocking {
        val access = plubJwtTokenRepository.getAccessToken()
        val refresh = plubJwtTokenRepository.getRefreshToken()

        mutex.withLock {
            if (verifyTokenIsRefreshed(access, refresh)) {
                Timber.tag(RETROFIT_TAG)
                    .d("TokenAuthenticator - authenticate() called / 중단된 API 재요청")
                response.request
                    .newBuilder()
                    .removeHeader("Authorization")
                    .header(
                        "Authorization",
                        "Bearer " + plubJwtTokenRepository.getAccessToken()
                    )
                    .build()
            } else null
        }
    }

    private suspend fun verifyTokenIsRefreshed(
        access: String,
        refresh: String
    ): Boolean {
        val newAccess = plubJwtTokenRepository.getAccessToken()

        return if (access != newAccess) true else {
            Timber.tag(RETROFIT_TAG)
                .d("TokenAuthenticator - authenticate() called / 토큰 만료. 토큰 Refresh 요청: $refresh")
            val plubJwtToken = plubJwtTokenRepository.reIssueToken(refresh)
            plubJwtTokenRepository.saveAccessTokenAndRefreshToken(
                plubJwtToken.data?.accessToken ?: "",
                plubJwtToken.data?.refreshToken ?: ""
            )
            val isTokenValid = plubJwtToken.data?.isTokenValid ?: false

            if(!isTokenValid)
                Timber.tag(RETROFIT_TAG)
                    .d("TokenAuthenticator - verifyTokenIsRefreshed() called / 토큰 갱신 실패.")

            isTokenValid
        }
    }
}