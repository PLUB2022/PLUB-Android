package com.plub.domain.repository

import com.plub.domain.UiState
import com.plub.domain.model.vo.notice.NoticeListVo
import com.plub.domain.model.vo.notice.NoticeVo
import com.plub.domain.model.vo.notice.PostNoticeWriteRequestVo
import kotlinx.coroutines.flow.Flow

interface NoticeRepository {
    suspend fun getAppNoticeList(request: Unit): Flow<UiState<NoticeListVo>>
    suspend fun getPlubingNoticeList(request: Int): Flow<UiState<NoticeListVo>>
    suspend fun postNoticeCreate(request: PostNoticeWriteRequestVo): Flow<UiState<Unit>>
    suspend fun putNoticeEdit(request: PostNoticeWriteRequestVo): Flow<UiState<NoticeVo>>
}