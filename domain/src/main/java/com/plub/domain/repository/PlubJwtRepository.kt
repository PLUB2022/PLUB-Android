package com.plub.domain.repository

import com.plub.domain.UiState
import com.plub.domain.model.vo.jwt.PlubJwtReIssueRequestVo
import com.plub.domain.model.vo.jwt.PlubJwtResponseVo
import com.plub.domain.model.vo.jwt.SavePlubJwtRequestVo
import kotlinx.coroutines.flow.Flow

interface PlubJwtRepository {
    fun saveAccessTokenAndRefreshToken(request: SavePlubJwtRequestVo): Flow<Boolean>

    fun getAccessToken(): Flow<String>

    fun getRefreshToken(): Flow<String>

    /**
     * 토큰 갱신 실패시 PlubJwtTokenData("", "")를 반환합니다.
     */
    suspend fun reIssueToken(request : PlubJwtReIssueRequestVo): Flow<UiState<PlubJwtResponseVo>>
}