package com.plub.domain.usecase

import com.plub.domain.UiState
import com.plub.domain.base.UseCase
import com.plub.domain.model.vo.jwt_token.JWTTokenReIssueRequestVo
import com.plub.domain.model.vo.jwt_token.PlubJwtTokenResponseVo
import com.plub.domain.repository.PlubJwtTokenRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class PostReIssueTokenUseCase @Inject constructor(
    private val plubJwtTokenRepository: PlubJwtTokenRepository
):UseCase<JWTTokenReIssueRequestVo, Flow<PlubJwtTokenResponseVo>>() {
    override operator fun invoke(request: JWTTokenReIssueRequestVo): Flow<PlubJwtTokenResponseVo> {
        return plubJwtTokenRepository.reIssueToken(request).map {
            when(it) {
                is UiState.Success -> it.data
                else -> PlubJwtTokenResponseVo("","")
            }
        }
    }
}