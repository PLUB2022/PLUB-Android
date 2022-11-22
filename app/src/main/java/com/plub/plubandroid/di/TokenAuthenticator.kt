package com.plub.plubandroid.di

import com.plub.data.api.PlubJwtTokenApi
import com.plub.data.model.PlubJwtTokenResponse
import com.plub.data.model.JWTTokenReIssueRequest
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
        val access = CoroutineScope(Dispatchers.IO).async {
            getAccessToken()
        }
        val refresh = CoroutineScope(Dispatchers.IO).async {
            getRefreshToken()
        }.toString()

        synchronized(this) {
            val newAccess = CoroutineScope(Dispatchers.IO).async{
                getAccessToken()
            }

            val isTokenRefreshed = if (access != newAccess) true else {
                Timber.tag(RETROFIT_TAG).d("TokenAuthenticator - authenticate() called / 토큰 만료. 토큰 Refresh 요청: $refresh")
                val tokenResponse = plubJwtTokenApi.reIssueToken(JWTTokenReIssueRequest(refresh))
                CoroutineScope(Dispatchers.IO).async{
                    handleResponse(tokenResponse)
                }
            }

            return if (isTokenRefreshed as Boolean) {
                Timber.tag(RETROFIT_TAG).d("TokenAuthenticator - authenticate() called / 중단된 API 재요청")
                response.request
                    .newBuilder()
                    .removeHeader("Authorization")
                    .header(
                        "Authorization",
                        "Bearer " +  CoroutineScope(Dispatchers.IO).async {
                            getRefreshToken()
                        }
                    )
                    .build()
            } else {
                null
            }
        }

    }

    private suspend fun handleResponse(tokenResponse: Response<PlubJwtTokenResponse>) =
        if (tokenResponse.isSuccessful) {
            if(tokenResponse.body() != null){
                plubJwtTokenRepository.saveAccessTokenAndRefreshToken(tokenResponse.body()!!.data!!.accessToken, tokenResponse.body()!!.data!!.refreshToken)
                true
            } else {
                Timber.tag(RETROFIT_TAG).d("TokenAuthenticator - handleResponse() called / tokenResponse의 body가 null 입니다.")
                false
            }

        } else {
            Timber.tag(RETROFIT_TAG).d("TokenAuthenticator - handleResponse() called / 리프레시 토큰이 만료되어 로그 아웃 되었습니다.")
            false
        }

    private suspend fun getAccessToken(): String {
        return plubJwtTokenRepository.getAccessToken()
    }

    private suspend fun getRefreshToken(): String {
        return plubJwtTokenRepository.getRefreshToken()
    }
}