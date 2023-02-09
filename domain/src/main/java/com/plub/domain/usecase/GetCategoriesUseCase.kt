package com.plub.domain.usecase

import com.plub.domain.UiState
import com.plub.domain.base.UseCase
import com.plub.domain.model.vo.home.CategoryListResponseVo
import com.plub.domain.model.vo.home.categorylistresponsevo.CategoryListDataResponseVo
import com.plub.domain.repository.CategoryListRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCategoriesUseCase @Inject constructor(
    private val categoryListRepository: CategoryListRepository,
) : UseCase<Unit, Flow<UiState<CategoryListDataResponseVo>>>(){
    override suspend fun invoke(request : Unit): Flow<UiState<CategoryListDataResponseVo>> {
        return categoryListRepository.getCategoryList()
    }
}