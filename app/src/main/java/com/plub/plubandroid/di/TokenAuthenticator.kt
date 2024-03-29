package com.plub.plubandroid.di

import com.plub.domain.UiState
import com.plub.domain.model.vo.jwt.PlubJwtReIssueRequestVo
import com.plub.domain.model.vo.jwt.PlubJwtResponseVo
import com.plub.domain.model.vo.jwt.SavePlubJwtRequestVo
import com.plub.domain.usecase.GetPlubAccessTokenUseCase
import com.plub.domain.usecase.GetPlubRefreshTokenUseCase
import com.plub.domain.usecase.PostReIssueTokenUseCase
import com.plub.domain.usecase.SavePlubAccessTokenAndRefreshTokenUseCase
import com.plub.plubandroid.util.RETROFIT_TAG
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Route
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TokenAuthenticator @Inject constructor(
    private val getPlubAccessTokenUseCase: GetPlubAccessTokenUseCase,
    private val getPlubRefreshTokenUseCase: GetPlubRefreshTokenUseCase,
    private val savePlubAccessTokenAndRefreshTokenUseCase: SavePlubAccessTokenAndRefreshTokenUseCase,
    private val postReIssueTokenUseCase: PostReIssueTokenUseCase
) : Authenticator {
    private val mutex = Mutex()

    override fun authenticate(route: Route?, response: okhttp3.Response): Request? = runBlocking {
        val access = async { getPlubAccessTokenUseCase(Unit).first() }
        val refresh = async { getPlubRefreshTokenUseCase(Unit).first() }
        val accessToken = access.await()
        val refreshToken = refresh.await()

        mutex.withLock {
            if (verifyTokenIsRefreshed(accessToken, refreshToken)) {
                Timber.tag(RETROFIT_TAG)
                    .d("TokenAuthenticator - authenticate() called / 중단된 API 재요청")
                response.request
                    .newBuilder()
                    .removeHeader("Authorization")
                    .header(
                        "Authorization",
                        "Bearer " + getPlubAccessTokenUseCase(Unit).first()
                    )
                    .build()
            } else null
        }
    }

    private suspend fun verifyTokenIsRefreshed(
        access: String,
        refresh: String
    ): Boolean {
        val newAccess = getPlubAccessTokenUseCase(Unit).first()

        return if (access != newAccess) true else {
            Timber.tag(RETROFIT_TAG).d("TokenAuthenticator - authenticate() called / 토큰 만료. 토큰 Refresh 요청: $refresh")
            val reIssueRequestVo = PlubJwtReIssueRequestVo(refresh)

            var plubJwtToken = PlubJwtResponseVo("", "")
            postReIssueTokenUseCase(reIssueRequestVo).collect { state ->
                when(state) {
                    is UiState.Loading -> { }
                    is UiState.Success -> {
                        plubJwtToken = state.data
                        return@collect
                    }
                    else -> {
                        return@collect
                    }
                }
            }

            val savePlubJwtRequestVo = SavePlubJwtRequestVo(plubJwtToken.accessToken, plubJwtToken.refreshToken)

            savePlubAccessTokenAndRefreshTokenUseCase(savePlubJwtRequestVo).first()
            plubJwtToken.isTokenValid.apply {
                if(!this) Timber.tag(RETROFIT_TAG).d("TokenAuthenticator - verifyTokenIsRefreshed() called / 토큰 갱신 실패.")
            }
        }
    }
}