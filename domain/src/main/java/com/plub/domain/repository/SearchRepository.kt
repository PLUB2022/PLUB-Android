package com.plub.domain.repository

import com.plub.domain.UiState
import com.plub.domain.model.vo.common.HobbyVo
import com.plub.domain.model.vo.home.search.RecentSearchVo
import com.plub.domain.model.vo.plub.PlubCardListVo
import com.plub.domain.model.vo.search.SearchPlubRecruitRequestVo
import kotlinx.coroutines.flow.Flow

interface SearchRepository {
    suspend fun getRecentSearches(): Flow<UiState<List<RecentSearchVo>>>
    suspend fun deleteRecentSearch(search: String): Flow<UiState<Unit>>
    suspend fun deleteAllRecentSearch(): Flow<UiState<Unit>>
    suspend fun insertRecentSearch(recentSearchVo: RecentSearchVo): Flow<UiState<Unit>>
    suspend fun searchPlubRecruit(request: SearchPlubRecruitRequestVo): Flow<UiState<PlubCardListVo>>
}