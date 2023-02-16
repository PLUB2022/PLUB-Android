package com.plub.domain.usecase

import com.plub.domain.UiState
import com.plub.domain.base.UseCase
import com.plub.domain.model.vo.board.PostBoardRequestVo
import com.plub.domain.repository.PlubingBoardRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PostPlubingBoardUseCase @Inject constructor(
    private val plubingBoardRepository: PlubingBoardRepository
) : UseCase<PostBoardRequestVo, Flow<UiState<Unit>>>() {
    override suspend operator fun invoke(request: PostBoardRequestVo): Flow<UiState<Unit>> {
        return plubingBoardRepository.postPlubingBoard(request)
    }
}