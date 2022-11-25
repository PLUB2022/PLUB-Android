package com.plub.domain.repository

import com.plub.domain.model.vo.jwt_token.PlubJwtTokenVo

interface PlubJwtTokenRepository {
    suspend fun saveAccessToken(accessToken: String)

    suspend fun saveAccessTokenAndRefreshToken(accessToken: String, refreshToken: String)

    suspend fun getAccessToken(): String

    suspend fun getRefreshToken(): String

    suspend fun reIssueToken(refreshToken : String): PlubJwtTokenVo
}