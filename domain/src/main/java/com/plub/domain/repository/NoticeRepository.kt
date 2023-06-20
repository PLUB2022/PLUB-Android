package com.plub.domain.repository

import com.plub.domain.UiState
import com.plub.domain.model.vo.board.BoardCommentListVo
import com.plub.domain.model.vo.board.BoardCommentVo
import com.plub.domain.model.vo.notice.GetNoticeCommentsRequestVo
import com.plub.domain.model.vo.notice.NoticeCommentCreateRequestVo
import com.plub.domain.model.vo.notice.NoticeCommentEditRequestVo
import com.plub.domain.model.vo.notice.NoticeListVo
import com.plub.domain.model.vo.notice.NoticeRequestVo
import com.plub.domain.model.vo.notice.NoticeVo
import com.plub.domain.model.vo.notice.PostNoticeWriteRequestVo
import kotlinx.coroutines.flow.Flow

interface NoticeRepository {
    suspend fun getAppNoticeList(): Flow<UiState<NoticeListVo>>
    suspend fun getAppNoticeDetail(request: NoticeRequestVo): Flow<UiState<NoticeVo>>
    suspend fun getPlubingNoticeList(requestPlubingId: Int, requestCursorId: Int): Flow<UiState<NoticeListVo>>
    suspend fun postNoticeCreate(request: PostNoticeWriteRequestVo): Flow<UiState<Unit>>
    suspend fun putNoticeEdit(request: PostNoticeWriteRequestVo): Flow<UiState<NoticeVo>>
    suspend fun getNoticeDetail(request: NoticeRequestVo): Flow<UiState<NoticeVo>>
    suspend fun deleteNotice(request: NoticeRequestVo): Flow<UiState<Unit>>
    suspend fun commentGetList(request: GetNoticeCommentsRequestVo): Flow<UiState<BoardCommentListVo>>
    suspend fun commentCreate(request: NoticeCommentCreateRequestVo): Flow<UiState<BoardCommentVo>>
    suspend fun commentDelete(request: NoticeRequestVo): Flow<UiState<Unit>>
    suspend fun commentEdit(request: NoticeCommentEditRequestVo): Flow<UiState<BoardCommentVo>>
}