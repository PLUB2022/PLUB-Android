package com.plub.domain.repository

import com.plub.domain.UiState
import com.plub.domain.model.vo.common.HobbyVo
import com.plub.domain.model.vo.home.search.RecentSearchVo
import kotlinx.coroutines.flow.Flow

interface SearchRepository {
    suspend fun getRecentSearches(): Flow<UiState<List<RecentSearchVo>>>
    suspend fun deleteRecentSearch(id:Int): Flow<UiState<Unit>>
    suspend fun deleteAllRecentSearch(): Flow<UiState<Unit>>
    suspend fun insertRecentSearch(currentSize:Int, recentSearchVo: RecentSearchVo): Flow<UiState<Unit>>
}