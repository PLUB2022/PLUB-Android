package com.plub.data.repository

import com.plub.data.api.BrowseApi
import com.plub.data.base.BaseRepository
import com.plub.data.mapper.PlubCardListResponseMapper
import com.plub.data.mapper.categoriesgatheringrequestmapper.CategoriesGatheringRequestMapper
import com.plub.domain.UiState
import com.plub.domain.model.vo.home.categoriesgatheringresponse.CategoriesGatheringRequestVo
import com.plub.domain.model.vo.plub.PlubCardListVo
import com.plub.domain.repository.CategoriesGatheringRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CategoriesGatheringRepositoryImpl @Inject constructor(private val browseApi: BrowseApi) : CategoriesGatheringRepository, BaseRepository() {
    override suspend fun getCategoriesGatheringList(request: CategoriesGatheringRequestVo): Flow<UiState<PlubCardListVo>> {
        val requestDto = CategoriesGatheringRequestMapper.mapDtoToModel(request)
        return apiLaunch(browseApi.fetchCategoriesGathering(
            requestDto.categoryId,
            requestDto.pageNumber,), PlubCardListResponseMapper)
    }
}