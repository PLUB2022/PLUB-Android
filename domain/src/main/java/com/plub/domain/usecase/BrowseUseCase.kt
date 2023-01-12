package com.plub.domain.usecase

import com.plub.domain.UiState
import com.plub.domain.base.UseCase
import com.plub.domain.model.vo.home.CategoryListResponseVo
import com.plub.domain.model.vo.home.HomePostRequestVo
import com.plub.domain.model.vo.home.HomePostResponseVo
import com.plub.domain.repository.CategoryListRepository
import com.plub.domain.repository.HomePostRepository
import com.plub.domain.repository.RecommendationGatheringRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class BrowseUseCase @Inject constructor(
    private val categoryListRepository: CategoryListRepository,
) : UseCase<CategoryListResponseVo, Flow<UiState<CategoryListResponseVo>>>(){
    suspend fun invoke(): Flow<UiState<CategoryListResponseVo>> {
        return categoryListRepository.getCategoryList()
    }

    override suspend fun invoke(request: CategoryListResponseVo): Flow<UiState<CategoryListResponseVo>> {
        //TODO("Not yet implemented")
        return categoryListRepository.getCategoryList()
    }


}