package com.plub.domain.usecase

import com.plub.domain.UiState
import com.plub.domain.base.UseCase
import com.plub.domain.model.vo.jwt.PlubJwtReIssueRequestVo
import com.plub.domain.model.vo.jwt.PlubJwtResponseVo
import com.plub.domain.repository.PlubJwtRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectIndexed
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class PostReIssueTokenUseCase @Inject constructor(
    private val plubJwtRepository: PlubJwtRepository
):UseCase<PlubJwtReIssueRequestVo, Flow<PlubJwtResponseVo>>() {
    override suspend operator fun invoke(request: PlubJwtReIssueRequestVo): Flow<PlubJwtResponseVo> = flow {
        plubJwtRepository.reIssueToken(request).collect {
            when(it) {
                is UiState.Loading -> {  }
                is UiState.Success -> emit(it.data)
                else -> emit(PlubJwtResponseVo("",""))
            }
        }
    }
}