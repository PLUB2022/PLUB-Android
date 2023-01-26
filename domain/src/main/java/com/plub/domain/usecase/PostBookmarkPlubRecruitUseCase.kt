package com.plub.domain.usecase

import com.plub.domain.UiState
import com.plub.domain.base.UseCase
import com.plub.domain.model.vo.bookmark.PlubBookmarkResponseVo
import com.plub.domain.repository.BookmarkRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PostBookmarkPlubRecruitUseCase @Inject constructor(
    private val bookmarkRepository: BookmarkRepository
) : UseCase<Int, Flow<UiState<PlubBookmarkResponseVo>>>() {
    override suspend fun invoke(request: Int): Flow<UiState<PlubBookmarkResponseVo>> {
        return bookmarkRepository.bookmarkPlubRecruit(request)
    }
}