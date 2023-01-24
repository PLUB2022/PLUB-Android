package com.plub.domain.usecase

import androidx.paging.PagingData
import com.plub.domain.base.UseCase
import com.plub.domain.model.vo.kakaoLocation.KakaoLocationInfoDocumentVo
import com.plub.domain.repository.KakaoLocationRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FetchKakaoLocationByKeywordUseCase @Inject constructor(
    private val locationRepository: KakaoLocationRepository
):UseCase<String, Flow<PagingData<KakaoLocationInfoDocumentVo>>>() {
    override suspend operator fun invoke(request: String): Flow<PagingData<KakaoLocationInfoDocumentVo>> {
        return locationRepository.fetchKakaoLocationByKeword(request)
    }
}