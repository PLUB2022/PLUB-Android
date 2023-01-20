package com.plub.data.repository

import com.plub.data.api.SearchApi
import com.plub.data.base.BaseRepository
import com.plub.data.dao.RecentSearchDao
import com.plub.data.mapper.RecentSearchResponseMapper
import com.plub.domain.UiState
import com.plub.domain.model.vo.home.search.RecentSearchVo
import com.plub.domain.repository.SearchRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SearchRepositoryImpl @Inject constructor(
    private val recentSearchDao: RecentSearchDao,
    private val searchApi: SearchApi,
) : SearchRepository, BaseRepository() {

    override suspend fun getRecentSearches(): Flow<UiState<List<RecentSearchVo>>> = recentSearchDao.getAll().map {
        val list = RecentSearchResponseMapper.mapDtoToModel(it)
        UiState.Success(list)
    }
}
