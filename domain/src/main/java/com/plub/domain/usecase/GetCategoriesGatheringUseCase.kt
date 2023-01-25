package com.plub.domain.usecase

import com.plub.domain.UiState
import com.plub.domain.base.UseCase
import com.plub.domain.model.vo.home.categoriesgatheringresponse.CategoriesGatheringRequestVo
import com.plub.domain.model.vo.home.recommendationgatheringvo.RecommendationGatheringResponseVo
import com.plub.domain.repository.CategoriesGatheringRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCategoriesGatheringUseCase @Inject constructor(
    private val categoriesGatheringRepository: CategoriesGatheringRepository,
) : UseCase<CategoriesGatheringRequestVo, Flow<UiState<RecommendationGatheringResponseVo>>>(){
    override suspend fun invoke(request : CategoriesGatheringRequestVo): Flow<UiState<RecommendationGatheringResponseVo>> {
        //TODO("Not yet implemented")
        return categoriesGatheringRepository.getCategoriesGatheringList(request)
    }


}