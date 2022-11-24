package com.plub.plubandroid.di

import com.plub.data.api.PlubJwtTokenApi
import com.plub.data.model.JWTTokenReIssueRequest
import com.plub.data.model.PlubJwtTokenResponse
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
    private val plubJwtTokenRepository: PlubJwtTokenRepository,
    private val plubJwtTokenApi: PlubJwtTokenApi
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
                        "Bearer " + plubJwtTokenRepository.getRefreshToken()
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
            val tokenResponse = plubJwtTokenApi.reIssueToken(JWTTokenReIssueRequest(refresh))
            handleResponse(tokenResponse)
        }
    }

    private suspend fun handleResponse(tokenResponse: Response<PlubJwtTokenResponse>) =
        when {
            !tokenResponse.isSuccessful -> {
                Timber.tag(RETROFIT_TAG)
                    .d("TokenAuthenticator - handleResponse() called / 리프레시 토큰이 만료되어 로그 아웃 되었습니다.")
                false
            }
            tokenResponse.body()?.data == null -> {
                Timber.tag(RETROFIT_TAG)
                    .d("TokenAuthenticator - handleResponse() called / Response Body 또는 data가 null입니다.")
                false
            }
            else -> {
                plubJwtTokenRepository.saveAccessTokenAndRefreshToken(
                    tokenResponse.body()?.data?.accessToken ?: "",
                    tokenResponse.body()?.data?.refreshToken ?: ""
                )
                true
            }
        }
}