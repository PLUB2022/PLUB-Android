package com.plub.data.repository

import com.plub.data.api.BrowseApi
import com.plub.data.base.BaseRepository
import com.plub.data.mapper.CategoryListResponseMapper
import com.plub.domain.UiState
import com.plub.domain.model.vo.home.CategoryListResponseVo
import com.plub.domain.repository.CategoryListRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CategoryListResposImpl @Inject constructor(private val browseApi: BrowseApi) : CategoryListRepository, BaseRepository() {
    override suspend fun getCategoryList(): Flow<UiState<CategoryListResponseVo>> {

        return apiLaunch(browseApi.browseCategoryList(), CategoryListResponseMapper)
    }
}