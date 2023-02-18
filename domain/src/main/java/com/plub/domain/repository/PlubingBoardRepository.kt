package com.plub.domain.repository

import com.plub.domain.UiState
import com.plub.domain.model.vo.board.BoardRequestVo
import com.plub.domain.model.vo.board.FetchPlubingBoardRequestVo
import com.plub.domain.model.vo.board.PlubingBoardListVo
import com.plub.domain.model.vo.board.PlubingBoardVo
import com.plub.domain.model.vo.board.PostBoardRequestVo
import kotlinx.coroutines.flow.Flow

interface PlubingBoardRepository {
    suspend fun getPlubingBoardList(request: FetchPlubingBoardRequestVo): Flow<UiState<PlubingBoardListVo>>
    suspend fun getPlubingPinList(plubingId:Int): Flow<UiState<List<PlubingBoardVo>>>
    suspend fun changePlubingPin(request:BoardRequestVo): Flow<UiState<Unit>>
    suspend fun deletePlubing(request:BoardRequestVo): Flow<UiState<Unit>>
    suspend fun postPlubingBoard(request: PostBoardRequestVo): Flow<UiState<Unit>>
}