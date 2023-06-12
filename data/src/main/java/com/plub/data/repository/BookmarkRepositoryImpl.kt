package com.plub.data.repository

import com.plub.data.api.BookmarkApi
import com.plub.data.base.BaseRepository
import com.plub.data.mapper.PlubBookmarkResponseMapper
import com.plub.data.mapper.PlubCardListResponseMapper
import com.plub.domain.UiState
import com.plub.domain.error.GatheringError
import com.plub.domain.model.vo.bookmark.PlubBookmarkResponseVo
import com.plub.domain.model.vo.plub.PlubCardListVo
import com.plub.domain.repository.BookmarkRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class BookmarkRepositoryImpl @Inject constructor(
    private val bookmarkApi: BookmarkApi,
) : BookmarkRepository, BaseRepository() {

    override suspend fun bookmarkPlubRecruit(id:Int): Flow<UiState<PlubBookmarkResponseVo>> {
        return apiLaunch(apiCall = { bookmarkApi.plubBookmark(id) }, PlubBookmarkResponseMapper){
            GatheringError.make(it)
        }
    }

    override suspend fun getMyPlubBookmarks(id: Int): Flow<UiState<PlubCardListVo>> {
        return apiLaunch(apiCall = { bookmarkApi.getMyPlubBookmarks(id) }, PlubCardListResponseMapper){
            GatheringError.make(it)
        }
    }
}
