package com.plub.domain.usecase

import com.plub.domain.base.UseCase
import com.plub.domain.model.vo.jwt_token.SavePlubJwtRequestVo
import com.plub.domain.repository.PlubJwtRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SavePlubAccessTokenAndRefreshTokenUseCase @Inject constructor(
    private val plubJwtRepository: PlubJwtRepository
):UseCase<SavePlubJwtRequestVo, Flow<Boolean>>() {
    override suspend operator fun invoke(request: SavePlubJwtRequestVo): Flow<Boolean> {
        return plubJwtRepository.saveAccessTokenAndRefreshToken(request)
    }
}