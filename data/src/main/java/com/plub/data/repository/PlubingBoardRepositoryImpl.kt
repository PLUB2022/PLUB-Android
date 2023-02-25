package com.plub.data.repository

import com.plub.data.api.PlubingBoardApi
import com.plub.data.base.BaseRepository
import com.plub.data.mapper.BoardCommentListResponseMapper
import com.plub.data.mapper.EditBoardRequestMapper
import com.plub.data.mapper.PlubingBoardListResponseMapper
import com.plub.data.mapper.PlubingBoardResponseMapper
import com.plub.data.mapper.PlubingPinListResponseMapper
import com.plub.data.mapper.PostBoardRequestMapper
import com.plub.data.mapper.PostCommentRequestMapper
import com.plub.data.mapper.UnitResponseMapper
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
import com.plub.domain.repository.PlubingBoardRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PlubingBoardRepositoryImpl @Inject constructor(private val boardApi: PlubingBoardApi) : PlubingBoardRepository, BaseRepository() {

    override suspend fun getPlubingBoardList(request: FetchPlubingBoardRequestVo): Flow<UiState<PlubingBoardListVo>> {
        return apiLaunch(boardApi.getBoardList(request.plubbingId, request.page), PlubingBoardListResponseMapper)
    }

    override suspend fun getPlubingPinList(plubingId: Int): Flow<UiState<List<PlubingBoardVo>>> {
        return apiLaunch(boardApi.getPinList(plubingId), PlubingPinListResponseMapper)
    }

    override suspend fun changePlubingPin(request: BoardRequestVo): Flow<UiState<Unit>> {
        return apiLaunch(boardApi.changePin(request.plubbingId, request.feedId), UnitResponseMapper)
    }

    override suspend fun deletePlubing(request: BoardRequestVo): Flow<UiState<Unit>> {
        return apiLaunch(boardApi.deletePlubing(request.plubbingId, request.feedId), UnitResponseMapper)
    }

    override suspend fun postPlubingBoard(request: PostBoardRequestVo): Flow<UiState<Unit>> {
        val body = PostBoardRequestMapper.mapModelToDto(request)
        return apiLaunch(boardApi.postBoard(request.plubingId, body), UnitResponseMapper)
    }

    override suspend fun getBoardDetail(request: BoardRequestVo): Flow<UiState<PlubingBoardVo>> {
        return apiLaunch(boardApi.getDetailBoard(request.plubbingId, request.feedId), PlubingBoardResponseMapper)
    }

    override suspend fun getCommentList(request: GetCommentListVo): Flow<UiState<BoardCommentListVo>> {
        return apiLaunch(boardApi.getCommentList(request.plubbingId, request.feedId, request.page), BoardCommentListResponseMapper)
    }

    override suspend fun postComment(request: PostCommentRequestVo): Flow<UiState<Unit>> {
        val body = PostCommentRequestMapper.mapModelToDto(request)
        return apiLaunch(boardApi.postComment(request.plubingId, request.feedId, body), UnitResponseMapper)
    }

    override suspend fun deleteComment(request: BoardRequestVo): Flow<UiState<Unit>> {
        return apiLaunch(boardApi.deleteComment(request.plubbingId, request.feedId, request.commentId), UnitResponseMapper)
    }

    override suspend fun editComment(request: BoardRequestVo): Flow<UiState<Unit>> {
        return apiLaunch(boardApi.editComment(request.plubbingId, request.feedId, request.commentId), UnitResponseMapper)
    }

    override suspend fun editBoard(request: EditBoardRequestVo): Flow<UiState<Unit>> {
        val body = EditBoardRequestMapper.mapModelToDto(request)
        return apiLaunch(boardApi.editBoard(request.plubingId, request.feedId, body), UnitResponseMapper)
    }
}