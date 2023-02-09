package com.plub.domain.repository

import com.plub.domain.UiState
import com.plub.domain.model.vo.home.CategoryListResponseVo
import com.plub.domain.model.vo.home.categorylistresponsevo.CategoryListDataResponseVo
import kotlinx.coroutines.flow.Flow

interface CategoryListRepository {
    suspend fun getCategoryList() : Flow<UiState<CategoryListDataResponseVo>>
}