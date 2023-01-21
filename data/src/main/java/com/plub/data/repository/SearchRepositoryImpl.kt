package com.plub.data.repository

import com.plub.data.api.SearchApi
import com.plub.data.base.BaseRepository
import com.plub.data.dao.RecentSearchDao
import com.plub.data.mapper.RecentSearchRequestMapper
import com.plub.data.mapper.RecentSearchResponseMapper
import com.plub.domain.UiState
import com.plub.domain.model.vo.home.search.RecentSearchVo
import com.plub.domain.repository.SearchRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SearchRepositoryImpl @Inject constructor(
    private val recentSearchDao: RecentSearchDao,
    private val searchApi: SearchApi,
) : SearchRepository, BaseRepository() {

    companion object {
        private const val RECENT_SEARCH_SELECT_COUNT = 10
        private const val RECENT_SEARCH_DELETE_OLDEST_COUNT = 1
        private const val RECENT_SEARCH_INSERT_MAX_COUNT = 10
    }

    override suspend fun getRecentSearches(): Flow<UiState<List<RecentSearchVo>>> {
        return recentSearchDao.getSearches(RECENT_SEARCH_SELECT_COUNT).map {
            val list = RecentSearchResponseMapper.mapDtoToModel(it)
            UiState.Success(list)
        }
    }

    override suspend fun deleteRecentSearch(id: Int): Flow<UiState<Unit>> = flow {
        recentSearchDao.deleteById(id)
        emit(UiState.Success(Unit))
    }

    override suspend fun deleteAllRecentSearch(): Flow<UiState<Unit>> = flow {
        recentSearchDao.deleteAll()
        emit(UiState.Success(Unit))
    }

    override suspend fun insertRecentSearch(
        currentSize: Int,
        recentSearchVo: RecentSearchVo
    ): Flow<UiState<Unit>> {
        val entity = RecentSearchRequestMapper.mapModelToDto(recentSearchVo)
        return if (currentSize == RECENT_SEARCH_INSERT_MAX_COUNT) flow {
            recentSearchDao.insert(entity)
            recentSearchDao.deleteOldestSearch(RECENT_SEARCH_DELETE_OLDEST_COUNT)
            emit(UiState.Success(Unit))
        } else flow {
            recentSearchDao.insert(entity)
            emit(UiState.Success(Unit))
        }
    }
}
