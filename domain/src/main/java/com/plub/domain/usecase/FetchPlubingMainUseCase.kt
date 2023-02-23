package com.plub.domain.usecase

import com.plub.domain.UiState
import com.plub.domain.base.UseCase
import com.plub.domain.model.vo.plub.PlubingMainVo
import com.plub.domain.repository.PlubingMainRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FetchPlubingMainUseCase @Inject constructor(
    private val plubingMainRepository: PlubingMainRepository
) : UseCase<Int, Flow<UiState<PlubingMainVo>>>() {
    override suspend operator fun invoke(request: Int): Flow<UiState<PlubingMainVo>> {
        return plubingMainRepository.getPlubingMain(request)
    }
}