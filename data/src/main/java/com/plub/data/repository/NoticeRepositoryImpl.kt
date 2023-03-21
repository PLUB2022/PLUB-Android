package com.plub.data.repository

import com.plub.data.api.NoticeApi
import com.plub.data.base.BaseRepository
import com.plub.data.mapper.UnitResponseMapper
import com.plub.data.mapper.notice.AppNoticeListResponseMapper
import com.plub.data.mapper.notice.NoticeWriteRequestMapper
import com.plub.data.mapper.notice.PlubingNoticeListResponseMapper
import com.plub.data.mapper.notice.PlubingNoticeResponseMapper
import com.plub.domain.UiState
import com.plub.domain.model.vo.notice.NoticeListVo
import com.plub.domain.model.vo.notice.NoticeVo
import com.plub.domain.model.vo.notice.PostNoticeWriteRequestVo
import com.plub.domain.repository.NoticeRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class NoticeRepositoryImpl @Inject constructor(private val noticeApi: NoticeApi) : NoticeRepository, BaseRepository() {

    override suspend fun getAppNoticeList(request: Unit): Flow<UiState<NoticeListVo>> {
        return apiLaunch(noticeApi.getAppNotice(), AppNoticeListResponseMapper)
    }

    override suspend fun getPlubingNoticeList(request: Int): Flow<UiState<NoticeListVo>> {
        return apiLaunch(noticeApi.getPlubingNotice(request), PlubingNoticeListResponseMapper)
    }

    override suspend fun postNoticeCreate(request: PostNoticeWriteRequestVo): Flow<UiState<Unit>> {
        val body = NoticeWriteRequestMapper.mapModelToDto(request)
        return apiLaunch(noticeApi.postPlubingCreateNotice(request.plubbingId, body), UnitResponseMapper)
    }

    override suspend fun putNoticeEdit(request: PostNoticeWriteRequestVo): Flow<UiState<NoticeVo>> {
        val body = NoticeWriteRequestMapper.mapModelToDto(request)
        return apiLaunch(noticeApi.putPlubingEditNotice(request.plubbingId, request.noticeId, body), PlubingNoticeResponseMapper)
    }
}