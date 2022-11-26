package com.plub.domain.usecase

import com.plub.domain.base.UseCase
import com.plub.domain.model.vo.jwt_token.SavePlubJwtTokenRequestVo
import com.plub.domain.repository.PlubJwtTokenRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SavePlubAccessTokenAndRefreshTokenUseCase @Inject constructor(
    private val plubJwtTokenRepository: PlubJwtTokenRepository
):UseCase<SavePlubJwtTokenRequestVo, Flow<Boolean>>() {
    override operator fun invoke(request: SavePlubJwtTokenRequestVo): Flow<Boolean> {
        return plubJwtTokenRepository.saveAccessTokenAndRefreshToken(request)
    }
}