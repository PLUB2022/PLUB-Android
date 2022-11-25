package com.plub.domain.usecase

import com.plub.domain.base.UseCase
import com.plub.domain.model.vo.jwt_token.PlubJwtTokenData
import com.plub.domain.repository.PlubJwtTokenRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SavePlubAccessTokenAndRefreshTokenUseCase @Inject constructor(
    private val plubJwtTokenRepository: PlubJwtTokenRepository
):UseCase<PlubJwtTokenData, Flow<Nothing>>() {
    override operator fun invoke(request: PlubJwtTokenData): Flow<Nothing> {
        return plubJwtTokenRepository.saveAccessTokenAndRefreshToken(request.accessToken, request.refreshToken)
    }
}