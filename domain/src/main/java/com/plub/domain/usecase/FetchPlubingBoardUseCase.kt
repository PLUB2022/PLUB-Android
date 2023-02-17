package com.plub.domain.usecase

import com.plub.domain.UiState
import com.plub.domain.base.UseCase
import com.plub.domain.model.vo.board.FetchPlubingBoardRequestVo
import com.plub.domain.model.vo.board.PlubingBoardListVo
import com.plub.domain.repository.PlubingBoardRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FetchPlubingBoardUseCase @Inject constructor(
    private val plubingBoardRepository: PlubingBoardRepository
) : UseCase<FetchPlubingBoardRequestVo, Flow<UiState<PlubingBoardListVo>>>() {
    override suspend operator fun invoke(request: FetchPlubingBoardRequestVo): Flow<UiState<PlubingBoardListVo>> {
        return plubingBoardRepository.getPlubingBoardList(request)
    }
}