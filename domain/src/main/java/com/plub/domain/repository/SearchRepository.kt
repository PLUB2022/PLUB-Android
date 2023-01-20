package com.plub.domain.repository

import com.plub.domain.UiState
import com.plub.domain.model.vo.common.HobbyVo
import com.plub.domain.model.vo.home.search.RecentSearchVo
import kotlinx.coroutines.flow.Flow

interface SearchRepository {
    suspend fun getRecentSearches(): Flow<UiState<List<RecentSearchVo>>>
}