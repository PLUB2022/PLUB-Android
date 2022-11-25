package com.plub.domain.usecase

import com.plub.domain.base.UseCase
import com.plub.domain.repository.PlubJwtTokenRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FetchPlubAccessTokenUseCase @Inject constructor(
    private val plubJwtTokenRepository: PlubJwtTokenRepository
):UseCase<Unit, Flow<String>>() {
    override operator fun invoke(request: Unit): Flow<String> {
        return plubJwtTokenRepository.getAccessToken()
    }
}