package com.plub.domain.repository

import com.plub.domain.UiState
import com.plub.domain.model.vo.jwt_token.JWTTokenReIssueRequestVo
import com.plub.domain.model.vo.jwt_token.PlubJwtTokenResponseVo
import com.plub.domain.model.vo.jwt_token.SavePlubJwtTokenRequestVo
import kotlinx.coroutines.flow.Flow

interface PlubJwtTokenRepository {
    fun saveAccessTokenAndRefreshToken(request: SavePlubJwtTokenRequestVo): Flow<Boolean>

    fun getAccessToken(): Flow<String>

    fun getRefreshToken(): Flow<String>

    /**
     * 토큰 갱신 실패시 PlubJwtTokenData("", "")를 반환합니다.
     */
    fun reIssueToken(request : JWTTokenReIssueRequestVo): Flow<UiState<PlubJwtTokenResponseVo>>
}