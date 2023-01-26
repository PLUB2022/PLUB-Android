package com.plub.data.repository

import com.plub.data.api.RecruitApi
import com.plub.data.base.BaseRepository
import com.plub.data.mapper.BookmarkMapper
import com.plub.domain.UiState
import com.plub.domain.model.vo.home.bookmarkvo.BookmarkResponseVo
import com.plub.domain.repository.BookmarkRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class BookmarkRepositoryImpl @Inject constructor(private val recruitApi: RecruitApi) : BookmarkRepository, BaseRepository() {
    override suspend fun bookmarkRecruit(request: Int): Flow<UiState<BookmarkResponseVo>> {
        return apiLaunch(recruitApi.bookmarkRecruit(request), BookmarkMapper)
    }
}