package com.plub.domain.repository

import androidx.paging.PagingData
import com.plub.domain.model.vo.kakaoLocation.KakaoLocationInfoDocumentVo
import kotlinx.coroutines.flow.Flow

interface KakaoLocationRepository {
    fun fetchKakaoLocationByKeword(request: String) : Flow<PagingData<KakaoLocationInfoDocumentVo>>
}