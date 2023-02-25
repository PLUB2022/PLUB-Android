package com.plub.domain.repository

import com.plub.domain.UiState
import com.plub.domain.model.vo.board.BoardCommentListVo
import com.plub.domain.model.vo.board.BoardRequestVo
import com.plub.domain.model.vo.board.EditBoardRequestVo
import com.plub.domain.model.vo.board.FetchPlubingBoardRequestVo
import com.plub.domain.model.vo.board.GetCommentListVo
import com.plub.domain.model.vo.board.PlubingBoardListVo
import com.plub.domain.model.vo.board.PlubingBoardVo
import com.plub.domain.model.vo.board.PostBoardRequestVo
import com.plub.domain.model.vo.board.PostCommentRequestVo
import kotlinx.coroutines.flow.Flow

interface PlubingBoardRepository {
    suspend fun getPlubingBoardList(request: FetchPlubingBoardRequestVo): Flow<UiState<PlubingBoardListVo>>
    suspend fun getPlubingPinList(plubingId:Int): Flow<UiState<List<PlubingBoardVo>>>
    suspend fun changePlubingPin(request:BoardRequestVo): Flow<UiState<Unit>>
    suspend fun deletePlubing(request:BoardRequestVo): Flow<UiState<Unit>>
    suspend fun postPlubingBoard(request: PostBoardRequestVo): Flow<UiState<Unit>>
    suspend fun getBoardDetail(request:BoardRequestVo): Flow<UiState<PlubingBoardVo>>
    suspend fun getCommentList(request: GetCommentListVo): Flow<UiState<BoardCommentListVo>>
    suspend fun postComment(request: PostCommentRequestVo): Flow<UiState<Unit>>
    suspend fun deleteComment(request:BoardRequestVo): Flow<UiState<Unit>>
    suspend fun editComment(request:BoardRequestVo): Flow<UiState<Unit>>
    suspend fun editBoard(request: EditBoardRequestVo): Flow<UiState<Unit>>
}