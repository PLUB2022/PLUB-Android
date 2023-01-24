package com.plub.domain.usecase

import com.plub.domain.UiState
import com.plub.domain.base.UseCase
import com.plub.domain.model.vo.jwt.PlubJwtReIssueRequestVo
import com.plub.domain.model.vo.jwt.PlubJwtResponseVo
import com.plub.domain.repository.PlubJwtRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PostReIssueTokenUseCase @Inject constructor(
    private val plubJwtRepository: PlubJwtRepository
):UseCase<PlubJwtReIssueRequestVo, Flow<UiState<PlubJwtResponseVo>>>() {
    override suspend operator fun invoke(request: PlubJwtReIssueRequestVo): Flow<UiState<PlubJwtResponseVo>> {
        return plubJwtRepository.reIssueToken(request)
    }
}