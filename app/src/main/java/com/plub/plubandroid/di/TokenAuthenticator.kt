package com.plub.plubandroid.di

import com.plub.data.api.PlubJwtTokenApi
import com.plub.data.model.PlubJwtTokenResponse
import com.plub.domain.repository.PlubJwtTokenRepository
import com.plub.plubandroid.util.RETROFIT_TAG
import kotlinx.coroutines.*
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Route
import retrofit2.Response
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TokenAuthenticator @Inject constructor(private val plubJwtTokenRepository: PlubJwtTokenRepository, private val plubJwtTokenApi: PlubJwtTokenApi) :
    Authenticator {
    override fun authenticate(route: Route?, response: okhttp3.Response): Request? {
        //return null
        val access = runBlocking {
            plubJwtTokenRepository.getAccessToken()
        }
        val refresh = runBlocking {
            plubJwtTokenRepository.getRefreshToken()
        }

        synchronized(this) {
            val newAccess = runBlocking {
                plubJwtTokenRepository.getAccessToken()
            }

            val isTokenRefreshed = if (access != newAccess) true else {
                Tdebug("TokenAuthenticator - authenticate() called / 토큰 만료. 토큰 Refresh 요청: $refresh")
//                //TODO 재발행하는 코드
//                val tokenResponse =
//                    runBlocking { api.refreshToken(RequestTokenBody(access, refresh)) }
//                handleResponse(tokenResponse)
                val tokenResponse =
                    runBlocking {
                        plubJwtTokenApi.reIssueToken(refresh)
                    }
                handleResponse(tokenResponse)
            }

            return if (isTokenRefreshed as Boolean) {
                Tdebug("TokenAuthenticator - authenticate() called / 중단된 API 재요청")
                response.request
                    .newBuilder()
                    .removeHeader("Authorization")
                    .header(
                        "Authorization",
                        "Bearer " + runBlocking { plubJwtTokenRepository.getRefreshToken() }
                    )
                    .build()
            } else {
                null
            }
        }

    }

    private fun handleResponse(tokenResponse: Response<PlubJwtTokenResponse>) =
        if (tokenResponse.isSuccessful) {
            runBlocking {
                plubJwtTokenRepository.saveAccessTokenAndRefreshToken(tokenResponse.body()!!.accesstoken, tokenResponse.body()!!.refreshtoken)
            }
            true
        } else {
            Tdebug("TokenAuthenticator - handleResponse() called / 리프레시 토큰이 만료되어 로그 아웃 되었습니다.")
            false
        }


    fun Tdebug( content : String){
        Timber.tag(RETROFIT_TAG).d(
            content
        )
    }
}