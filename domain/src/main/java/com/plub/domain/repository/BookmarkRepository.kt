package com.plub.domain.repository

import com.plub.domain.UiState
import com.plub.domain.model.vo.home.bookmarkvo.BookmarkResponseVo
import kotlinx.coroutines.flow.Flow

interface BookmarkRepository {
    suspend fun bookmarkRecruit(request : Int) : Flow<UiState<BookmarkResponseVo>>
}