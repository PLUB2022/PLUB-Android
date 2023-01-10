package com.plub.data.repository

import com.plub.data.UiStateCallback
import com.plub.data.api.BrowseApi
import com.plub.data.base.BaseRepository
import com.plub.data.mapper.CategoryListResponseMapper
import com.plub.domain.UiState
import com.plub.domain.error.UiError
import com.plub.domain.model.vo.home.CategoryListResponseVo
import com.plub.domain.repository.CategoryListRepository
import com.plub.domain.result.LoginFailure
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

class CategoryListResposImpl @Inject constructor(private val browseApi: BrowseApi) : CategoryListRepository, BaseRepository() {
    override fun getCategoryList(): Flow<UiState<CategoryListResponseVo>> = flow {
        request(browseApi.browseCategoryList(), CategoryListResponseMapper, object : UiStateCallback<CategoryListResponseVo>() {
            override suspend fun onSuccess(state: UiState.Success<CategoryListResponseVo>, customCode: Int) {
                val uiState = super.uiStateMapResult(state) {
                    LoginFailure.make(customCode)
                }
                emit(uiState)
            }

            override suspend fun onError(state: UiState.Error) {
                emit(state)
            }
        })
    }.onStart { emit(UiState.Loading) }.catch { e:Throwable ->
        e.printStackTrace()
        emit(UiState.Error(UiError.Invalided))
    }
}