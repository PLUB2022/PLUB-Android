package com.plub.domain.repository

import com.plub.domain.UiState
import com.plub.domain.model.vo.common.HobbyVo
import com.plub.domain.model.vo.common.SubHobbyVo
import com.plub.domain.model.vo.home.categoryResponseVo.CategoryListDataResponseVo
import com.plub.domain.model.vo.home.interestRegisterVo.RegisterInterestResponseVo
import kotlinx.coroutines.flow.Flow

interface HobbyRepository {
    suspend fun getCategoryList() : Flow<UiState<CategoryListDataResponseVo>>
    suspend fun allHobbies(): Flow<UiState<List<HobbyVo>>>
    suspend fun subHobbies(request : Int): Flow<UiState<List<SubHobbyVo>>>
    suspend fun registerInterest(request : List<Int>) : Flow<UiState<RegisterInterestResponseVo>>
    suspend fun browseInterest() : Flow<UiState<RegisterInterestResponseVo>>
}