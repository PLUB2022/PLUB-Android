package com.plub.domain.repository

interface PlubJwtTokenRepository {
    suspend fun saveAccessToken(accessToken: String)

    suspend fun saveAccessTokenAndRefreshToken(accessToken: String, refreshToken: String)

    suspend fun getAccessToken(): String

    suspend fun getRefreshToken(): String
}