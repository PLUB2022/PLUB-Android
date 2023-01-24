package com.plub.data.repository

import com.plub.data.api.SearchApi
import com.plub.data.base.BaseRepository
import com.plub.data.dao.RecentSearchDao
import com.plub.data.mapper.PlubCardListResponseMapper
import com.plub.data.mapper.RecentSearchRequestMapper
import com.plub.data.mapper.RecentSearchResponseMapper
import com.plub.data.mapper.SearchPlubRecruitRequestMapper
import com.plub.domain.UiState
import com.plub.domain.error.SearchError
import com.plub.domain.model.vo.home.search.RecentSearchVo
import com.plub.domain.model.vo.plub.PlubCardListVo
import com.plub.domain.model.vo.search.SearchPlubRecruitRequestVo
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

    override suspend fun deleteRecentSearch(search: String): Flow<UiState<Unit>> = flow {
        recentSearchDao.deleteBySearch(search)
        emit(UiState.Success(Unit))
    }

    override suspend fun deleteAllRecentSearch(): Flow<UiState<Unit>> = flow {
        recentSearchDao.deleteAll()
        emit(UiState.Success(Unit))
    }

    override suspend fun insertRecentSearch(
        recentSearchVo: RecentSearchVo
    ): Flow<UiState<Unit>> {
        val entity = RecentSearchRequestMapper.mapModelToDto(recentSearchVo)
        return flow {
            recentSearchDao.insert(entity)
            val searchesCount = recentSearchDao.getSearchesCount()
            if (searchesCount > RECENT_SEARCH_INSERT_MAX_COUNT) {
                recentSearchDao.deleteOldestSearch(RECENT_SEARCH_DELETE_OLDEST_COUNT)
                emit(UiState.Success(Unit))
            } else {
                emit(UiState.Success(Unit))
            }
        }
    }

    override suspend fun searchPlubRecruit(request: SearchPlubRecruitRequestVo): Flow<UiState<PlubCardListVo>> {
        val requestMap = SearchPlubRecruitRequestMapper.mapModelToDto(request)
        return apiLaunch(searchApi.plubSearch(requestMap), PlubCardListResponseMapper) {
            SearchError.make(it)
        }
    }
}
