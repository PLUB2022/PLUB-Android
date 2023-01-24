package com.plub.domain.repository

import androidx.paging.PagingData
import com.plub.domain.UiState
import com.plub.domain.model.vo.home.HomePostRequestVo
import com.plub.domain.model.vo.home.HomePostResponseVo
import com.plub.domain.model.vo.home.SampleCategory
import com.plub.domain.model.vo.kakaoLocation.KakaoLocationInfoDocumentVo
import com.plub.domain.model.vo.kakaoLocation.KakaoLocationInfoVo
import kotlinx.coroutines.flow.Flow

interface KakaoLocationRepository {
    fun fetchKakaoLocationByKeword(request: String) : Flow<PagingData<KakaoLocationInfoDocumentVo>>
}