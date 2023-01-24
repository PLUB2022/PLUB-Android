package com.plub.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.plub.data.api.KakaoLocationApi
import com.plub.data.base.BaseRepository
import com.plub.data.pagingSource.KakaoLocationPagingSource
import com.plub.domain.UiState
import com.plub.domain.model.vo.home.HomePostResponseVo
import com.plub.domain.model.vo.kakaoLocation.KakaoLocationInfoDocumentVo
import com.plub.domain.model.vo.kakaoLocation.KakaoLocationInfoVo
import com.plub.domain.repository.KakaoLocationRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

class KakaoLocationRepositoryImpl @Inject constructor(private val kakaoLocationApi: KakaoLocationApi)
    :KakaoLocationRepository {
    override fun fetchKakaoLocationByKeword(request: String): Flow<PagingData<KakaoLocationInfoDocumentVo>> {
        return Pager(
            config = PagingConfig(pageSize = 15),
            pagingSourceFactory = { KakaoLocationPagingSource(kakaoLocationApi, request) }
        ).flow
    }
}