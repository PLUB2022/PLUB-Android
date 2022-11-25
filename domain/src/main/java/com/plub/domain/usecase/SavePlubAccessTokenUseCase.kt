package com.plub.domain.usecase

import com.plub.domain.base.UseCase
import com.plub.domain.repository.PlubJwtTokenRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SavePlubAccessTokenUseCase @Inject constructor(
    private val plubJwtTokenRepository: PlubJwtTokenRepository
):UseCase<String, Flow<Nothing>>() {
    override operator fun invoke(request: String): Flow<Nothing> {
        return plubJwtTokenRepository.saveAccessToken(request)
    }
}