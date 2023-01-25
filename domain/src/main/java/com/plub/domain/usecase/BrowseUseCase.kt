package com.plub.domain.usecase

import com.plub.domain.UiState
import com.plub.domain.base.UseCase
import com.plub.domain.model.vo.home.CategoryListResponseVo
import com.plub.domain.repository.CategoryListRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class BrowseUseCase @Inject constructor(
    private val categoryListRepository: CategoryListRepository,
) : UseCase<CategoryListResponseVo, Flow<UiState<CategoryListResponseVo>>>(){
    fun invoke(): Flow<UiState<CategoryListResponseVo>> {
        return categoryListRepository.getCategoryList()
    }

    override fun invoke(request: CategoryListResponseVo): Flow<UiState<CategoryListResponseVo>> {
        //TODO("Not yet implemented")
        return categoryListRepository.getCategoryList()
    }


}