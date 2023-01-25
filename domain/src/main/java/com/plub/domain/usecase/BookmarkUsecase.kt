
package com.plub.domain.usecase

import com.plub.domain.UiState
import com.plub.domain.base.UseCase
import com.plub.domain.model.vo.home.bookmarkvo.BookmarkResponseVo
import com.plub.domain.repository.BookmarkRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class BookmarkUsecase @Inject constructor(
    private val bookmarkRepository: BookmarkRepository
) : UseCase<Int, Flow<UiState<BookmarkResponseVo>>>() {
    override suspend fun invoke(request: Int): Flow<UiState<BookmarkResponseVo>> {
        return bookmarkRepository.bookmarkRecruit(request)
    }
}