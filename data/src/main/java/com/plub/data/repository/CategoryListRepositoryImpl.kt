package com.plub.data.repository

import com.plub.data.api.HomeBrowseApi
import com.plub.data.base.BaseRepository
import com.plub.data.mapper.CategoryListResponseMapper
import com.plub.domain.UiState
import com.plub.domain.model.vo.home.categoryResponseVo.CategoryListDataResponseVo
import com.plub.domain.repository.CategoryListRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CategoryListRepositoryImpl @Inject constructor(private val browseApi: HomeBrowseApi) : CategoryListRepository, BaseRepository() {
    override suspend fun getCategoryList(): Flow<UiState<CategoryListDataResponseVo>> {
        return apiLaunch(browseApi.fetchCategoryList(), CategoryListResponseMapper)
    }
}