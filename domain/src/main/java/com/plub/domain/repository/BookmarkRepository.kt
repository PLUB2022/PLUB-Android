package com.plub.domain.repository

import com.plub.domain.UiState
import com.plub.domain.model.vo.bookmark.PlubBookmarkResponseVo
import kotlinx.coroutines.flow.Flow

interface BookmarkRepository {
    suspend fun bookmarkPlubRecruit(id:Int): Flow<UiState<PlubBookmarkResponseVo>>
}