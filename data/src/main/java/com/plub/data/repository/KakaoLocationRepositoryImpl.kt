package com.plub.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.plub.data.api.KakaoLocationApi
import com.plub.data.pagingSource.KakaoLocationPagingSource
import com.plub.domain.model.vo.kakaoLocation.KakaoLocationInfoDocumentVo
import com.plub.domain.repository.KakaoLocationRepository
import kotlinx.coroutines.flow.Flow
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