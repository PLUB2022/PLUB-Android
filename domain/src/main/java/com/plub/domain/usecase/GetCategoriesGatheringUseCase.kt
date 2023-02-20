package com.plub.domain.usecase

import com.plub.domain.UiState
import com.plub.domain.base.UseCase
import com.plub.domain.model.vo.home.categoriesGatheringResponse.CategoriesGatheringRequestVo
import com.plub.domain.model.vo.plub.PlubCardListVo
import com.plub.domain.repository.RecommendationGatheringRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCategoriesGatheringUseCase @Inject constructor(
    private val recommendationGatheringRepository: RecommendationGatheringRepository,
) : UseCase<CategoriesGatheringRequestVo, Flow<UiState<PlubCardListVo>>>(){
    override suspend fun invoke(request : CategoriesGatheringRequestVo): Flow<UiState<PlubCardListVo>> {
        return recommendationGatheringRepository.getCategoriesGatheringList(request)
    }
}