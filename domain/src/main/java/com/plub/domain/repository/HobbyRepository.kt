package com.plub.domain.repository

import com.plub.domain.UiState
import com.plub.domain.model.vo.common.HobbyVo
import com.plub.domain.model.vo.common.SubHobbyVo
import kotlinx.coroutines.flow.Flow

interface HobbyRepository {
    suspend fun allHobbies(): Flow<UiState<List<HobbyVo>>>
    suspend fun subHobbies(request : Int): Flow<UiState<List<SubHobbyVo>>>
}