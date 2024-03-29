package com.plub.data.repository

import com.plub.data.api.PlubingBoardApi
import com.plub.data.base.BaseRepository
import com.plub.data.mapper.BoardCommentListResponseMapper
import com.plub.data.mapper.BoardCommentResponseMapper
import com.plub.data.mapper.BoardEditRequestMapper
import com.plub.data.mapper.PlubingBoardListResponseMapper
import com.plub.data.mapper.PlubingBoardResponseMapper
import com.plub.data.mapper.PlubingPinListResponseMapper
import com.plub.data.mapper.BoardCreateRequestMapper
import com.plub.data.mapper.CommentCreateRequestMapper
import com.plub.data.mapper.CommentEditRequestMapper
import com.plub.data.mapper.UnitResponseMapper
import com.plub.domain.UiState
import com.plub.domain.model.vo.board.BoardCommentListVo
import com.plub.domain.model.vo.board.BoardCommentVo
import com.plub.domain.model.vo.board.BoardRequestVo
import com.plub.domain.model.vo.board.BoardEditRequestVo
import com.plub.domain.model.vo.board.GetBoardFeedsRequestVo
import com.plub.domain.model.vo.board.GetBoardCommentsRequestVo
import com.plub.domain.model.vo.board.PlubingBoardListVo
import com.plub.domain.model.vo.board.PlubingBoardVo
import com.plub.domain.model.vo.board.BoardCreateRequestVo
import com.plub.domain.model.vo.board.CommentCreateRequestVo
import com.plub.domain.model.vo.board.CommentEditRequestVo
import com.plub.domain.repository.PlubingBoardRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PlubingBoardRepositoryImpl @Inject constructor(private val boardApi: PlubingBoardApi) : PlubingBoardRepository, BaseRepository() {

    override suspend fun feedGetList(request: GetBoardFeedsRequestVo): Flow<UiState<PlubingBoardListVo>> {
        return apiLaunch(apiCall = { boardApi.getFeeds(request.plubbingId, request.cursorId) }, PlubingBoardListResponseMapper)
    }

    override suspend fun feedGetPinedList(plubingId: Int): Flow<UiState<List<PlubingBoardVo>>> {
        return apiLaunch(apiCall = { boardApi.getPins(plubingId) }, PlubingPinListResponseMapper)
    }

    override suspend fun feedChangePin(request: BoardRequestVo): Flow<UiState<Unit>> {
        return apiLaunch(apiCall = { boardApi.changePin(request.plubbingId, request.feedId) }, UnitResponseMapper)
    }

    override suspend fun feedDelete(request: BoardRequestVo): Flow<UiState<Unit>> {
        return apiLaunch(apiCall = { boardApi.deleteFeed(request.plubbingId, request.feedId) }, UnitResponseMapper)
    }

    override suspend fun feedCreate(request: BoardCreateRequestVo): Flow<UiState<Unit>> {
        val body = BoardCreateRequestMapper.mapModelToDto(request)
        return apiLaunch(apiCall = { boardApi.createFeed(request.plubingId, body) }, UnitResponseMapper)
    }

    override suspend fun feedDetail(request: BoardRequestVo): Flow<UiState<PlubingBoardVo>> {
        return apiLaunch(apiCall = { boardApi.detailFeed(request.plubbingId, request.feedId) }, PlubingBoardResponseMapper)
    }

    override suspend fun feedEdit(request: BoardEditRequestVo): Flow<UiState<PlubingBoardVo>> {
        val body = BoardEditRequestMapper.mapModelToDto(request)
        return apiLaunch(apiCall = { boardApi.editFeed(request.plubingId, request.feedId, body) }, PlubingBoardResponseMapper)
    }

    override suspend fun commentGetList(request: GetBoardCommentsRequestVo): Flow<UiState<BoardCommentListVo>> {
        return apiLaunch(apiCall = { boardApi.getComments(request.plubbingId, request.feedId, request.cursorId) }, BoardCommentListResponseMapper)
    }

    override suspend fun commentCreate(request: CommentCreateRequestVo): Flow<UiState<BoardCommentVo>> {
        val body = CommentCreateRequestMapper.mapModelToDto(request)
        return apiLaunch(apiCall = { boardApi.createComment(request.plubingId, request.feedId, body) }, BoardCommentResponseMapper)
    }

    override suspend fun commentDelete(request: BoardRequestVo): Flow<UiState<Unit>> {
        return apiLaunch(apiCall = { boardApi.deleteComment(request.plubbingId, request.feedId, request.commentId) }, UnitResponseMapper)
    }

    override suspend fun commentEdit(request: CommentEditRequestVo): Flow<UiState<BoardCommentVo>> {
        val body = CommentEditRequestMapper.mapModelToDto(request)
        return apiLaunch(apiCall = { boardApi.editComment(request.plubingId, request.feedId, request.commentId, body) }, BoardCommentResponseMapper)
    }
}