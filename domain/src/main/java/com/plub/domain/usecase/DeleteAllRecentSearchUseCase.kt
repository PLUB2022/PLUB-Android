package com.plub.domain.usecase

import com.plub.domain.UiState
import com.plub.domain.base.UseCase
import com.plub.domain.model.vo.home.search.RecentSearchVo
import com.plub.domain.repository.SearchRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DeleteAllRecentSearchUseCase @Inject constructor(
    private val searchRepository: SearchRepository
) : UseCase<Unit, Flow<UiState<Unit>>>() {
    override suspend operator fun invoke(request: Unit): Flow<UiState<Unit>> {
        return searchRepository.deleteAllRecentSearch()
    }
}