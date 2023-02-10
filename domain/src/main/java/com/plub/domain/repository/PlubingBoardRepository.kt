package com.plub.domain.repository

import com.plub.domain.UiState
import com.plub.domain.model.vo.board.FetchPlubingBoardRequestVo
import com.plub.domain.model.vo.board.PlubingBoardListVo
import com.plub.domain.model.vo.board.PlubingBoardVo
import kotlinx.coroutines.flow.Flow

interface PlubingBoardRepository {
    suspend fun getPlubingBoardList(request: FetchPlubingBoardRequestVo): Flow<UiState<PlubingBoardListVo>>
    suspend fun getPlubingPinList(id:Int): Flow<UiState<List<PlubingBoardVo>>>
    suspend fun changePlubingPin(id:Int): Flow<UiState<Unit>>
    suspend fun deletePlubing(id:Int): Flow<UiState<Unit>>
}