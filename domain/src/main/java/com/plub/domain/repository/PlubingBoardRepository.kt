package com.plub.domain.repository

import com.plub.domain.UiState
import com.plub.domain.model.vo.board.BoardCommentListVo
import com.plub.domain.model.vo.board.BoardRequestVo
import com.plub.domain.model.vo.board.BoardEditRequestVo
import com.plub.domain.model.vo.board.GetBoardFeedsRequestVo
import com.plub.domain.model.vo.board.GetBoardCommentsRequestVo
import com.plub.domain.model.vo.board.PlubingBoardListVo
import com.plub.domain.model.vo.board.PlubingBoardVo
import com.plub.domain.model.vo.board.BoardCreateRequestVo
import com.plub.domain.model.vo.board.CommentCreateRequestVo
import com.plub.domain.model.vo.board.CommentEditRequestVo
import kotlinx.coroutines.flow.Flow

interface PlubingBoardRepository {
    suspend fun feedGetList(request: GetBoardFeedsRequestVo): Flow<UiState<PlubingBoardListVo>>
    suspend fun feedGetPinedList(plubingId:Int): Flow<UiState<List<PlubingBoardVo>>>
    suspend fun feedChangePin(request:BoardRequestVo): Flow<UiState<Unit>>
    suspend fun feedDelete(request:BoardRequestVo): Flow<UiState<Unit>>
    suspend fun feedDetail(request:BoardRequestVo): Flow<UiState<PlubingBoardVo>>
    suspend fun feedCreate(request: BoardCreateRequestVo): Flow<UiState<Unit>>
    suspend fun feedEdit(request: BoardEditRequestVo): Flow<UiState<Unit>>
    suspend fun commentGetList(request: GetBoardCommentsRequestVo): Flow<UiState<BoardCommentListVo>>
    suspend fun commentCreate(request: CommentCreateRequestVo): Flow<UiState<Unit>>
    suspend fun commentDelete(request:BoardRequestVo): Flow<UiState<Unit>>
    suspend fun commentEdit(request: CommentEditRequestVo): Flow<UiState<Unit>>
}