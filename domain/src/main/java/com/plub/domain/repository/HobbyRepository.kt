package com.plub.domain.repository

import com.plub.domain.UiState
import com.plub.domain.model.vo.common.HobbyVo
import kotlinx.coroutines.flow.Flow

interface HobbyRepository {
    fun allHobbies(): Flow<UiState<List<HobbyVo>>>
}