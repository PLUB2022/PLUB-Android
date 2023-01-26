package com.plub.domain.usecase

import com.plub.domain.UiState
import com.plub.domain.base.UseCase
import com.plub.domain.model.vo.plub.PlubCardListVo
import com.plub.domain.repository.BookmarkRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMyPlubBookmarksUseCase @Inject constructor(
    private val bookmarkRepository: BookmarkRepository
) : UseCase<Unit, Flow<UiState<PlubCardListVo>>>() {
    override suspend fun invoke(request: Unit): Flow<UiState<PlubCardListVo>> {
        return bookmarkRepository.getMyPlubBookmarks()
    }
}