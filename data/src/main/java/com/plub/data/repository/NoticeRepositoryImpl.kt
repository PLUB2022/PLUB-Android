package com.plub.data.repository

import com.plub.data.api.NoticeApi
import com.plub.data.base.BaseRepository
import com.plub.data.mapper.BoardCommentListResponseMapper
import com.plub.data.mapper.BoardCommentResponseMapper
import com.plub.data.mapper.UnitResponseMapper
import com.plub.data.mapper.notice.AppNoticeListResponseMapper
import com.plub.data.mapper.notice.AppNoticeResponseMapper
import com.plub.data.mapper.notice.NoticeCommentCreateRequestMapper
import com.plub.data.mapper.notice.NoticeCommentEditRequestMapper
import com.plub.data.mapper.notice.NoticeWriteRequestMapper
import com.plub.data.mapper.notice.PlubingNoticeListResponseMapper
import com.plub.data.mapper.notice.PlubingNoticeResponseMapper
import com.plub.domain.UiState
import com.plub.domain.error.FeedError
import com.plub.domain.error.GatheringError
import com.plub.domain.error.NoticeError
import com.plub.domain.model.vo.board.BoardCommentListVo
import com.plub.domain.model.vo.board.BoardCommentVo
import com.plub.domain.model.vo.notice.GetNoticeCommentsRequestVo
import com.plub.domain.model.vo.notice.NoticeCommentCreateRequestVo
import com.plub.domain.model.vo.notice.NoticeCommentEditRequestVo
import com.plub.domain.model.vo.notice.NoticeListVo
import com.plub.domain.model.vo.notice.NoticeRequestVo
import com.plub.domain.model.vo.notice.NoticeVo
import com.plub.domain.model.vo.notice.PostNoticeWriteRequestVo
import com.plub.domain.repository.NoticeRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class NoticeRepositoryImpl @Inject constructor(private val noticeApi: NoticeApi) : NoticeRepository, BaseRepository() {

    override suspend fun getAppNoticeList(): Flow<UiState<NoticeListVo>> {
        return apiLaunch(apiCall = { noticeApi.getAppNotice() }, AppNoticeListResponseMapper)
    }

    override suspend fun getAppNoticeDetail(request: NoticeRequestVo): Flow<UiState<NoticeVo>> {
        return apiLaunch(apiCall = { noticeApi.getAppNoticeDetail(request.noticeId) }, AppNoticeResponseMapper){
            NoticeError.make(it)
        }
    }

    override suspend fun getPlubingNoticeList(requestPlubingId: Int, requestCursorId: Int): Flow<UiState<NoticeListVo>> {
        return apiLaunch(apiCall = { noticeApi.getPlubingNotice(cursorId = requestCursorId, plubbingId = requestPlubingId) }, PlubingNoticeListResponseMapper){
            GatheringError.make(it)
        }
    }

    override suspend fun postNoticeCreate(request: PostNoticeWriteRequestVo): Flow<UiState<Unit>> {
        val body = NoticeWriteRequestMapper.mapModelToDto(request)
        return apiLaunch(apiCall = { noticeApi.postPlubingCreateNotice(request.plubbingId, body) }, UnitResponseMapper){
            GatheringError.make(it)
        }
    }

    override suspend fun putNoticeEdit(request: PostNoticeWriteRequestVo): Flow<UiState<NoticeVo>> {
        val body = NoticeWriteRequestMapper.mapModelToDto(request)
        return apiLaunch(apiCall = { noticeApi.putPlubingEditNotice(request.plubbingId, request.noticeId, body) }, PlubingNoticeResponseMapper){
            GatheringError.make(it)
        }
    }

    override suspend fun getNoticeDetail(request: NoticeRequestVo): Flow<UiState<NoticeVo>> {
        return apiLaunch(apiCall = { noticeApi.getNoticeDetail(request.plubbingId, request.noticeId) }, PlubingNoticeResponseMapper){
            NoticeError.make(it)
        }
    }

    override suspend fun deleteNotice(request: NoticeRequestVo): Flow<UiState<Unit>> {
        return apiLaunch(apiCall = { noticeApi.deleteNotice(request.plubbingId, request.noticeId) }, UnitResponseMapper){
            GatheringError.make(it)
        }
    }

    override suspend fun commentCreate(request: NoticeCommentCreateRequestVo): Flow<UiState<BoardCommentVo>> {
        val body = NoticeCommentCreateRequestMapper.mapModelToDto(request)
        return apiLaunch(apiCall = { noticeApi.createComment(request.plubbingId, request.noticeId,body) }, BoardCommentResponseMapper){
            NoticeError.make(it)
        }
    }

    override suspend fun commentDelete(request: NoticeRequestVo): Flow<UiState<Unit>> {
        return apiLaunch(apiCall = { noticeApi.deleteComment(request.plubbingId, request.noticeId, request.commentId) }, UnitResponseMapper){
            FeedError.make(it)
        }
    }

    override suspend fun commentEdit(request: NoticeCommentEditRequestVo): Flow<UiState<BoardCommentVo>> {
        val body = NoticeCommentEditRequestMapper.mapModelToDto(request)
        return apiLaunch(apiCall = { noticeApi.editComment(request.plubingId, request.noticeId, request.commentId, body) }, BoardCommentResponseMapper){
            FeedError.make(it)
        }
    }

    override suspend fun commentGetList(request: GetNoticeCommentsRequestVo): Flow<UiState<BoardCommentListVo>> {
        return apiLaunch(apiCall = { noticeApi.getComments(request.plubbingId, request.noticeId, request.cursorId) }, BoardCommentListResponseMapper){
            GatheringError.make(it)
        }
    }
}