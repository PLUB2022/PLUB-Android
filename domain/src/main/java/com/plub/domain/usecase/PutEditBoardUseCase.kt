package com.plub.domain.usecase

import com.plub.domain.UiState
import com.plub.domain.base.UseCase
import com.plub.domain.model.vo.board.BoardRequestVo
import com.plub.domain.model.vo.board.EditBoardRequestVo
import com.plub.domain.model.vo.board.FetchPlubingBoardRequestVo
import com.plub.domain.model.vo.board.PlubingBoardListVo
import com.plub.domain.repository.PlubingBoardRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PutEditBoardUseCase @Inject constructor(
    private val plubingBoardRepository: PlubingBoardRepository
) : UseCase<EditBoardRequestVo, Flow<UiState<Unit>>>() {
    override suspend operator fun invoke(request: EditBoardRequestVo): Flow<UiState<Unit>> {
        return plubingBoardRepository.editBoard(request)
    }
}