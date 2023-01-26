package com.plub.domain.usecase

import com.plub.domain.UiState
import com.plub.domain.base.UseCase
import com.plub.domain.model.vo.plub.PlubCardListVo
import com.plub.domain.model.vo.search.SearchPlubRecruitRequestVo
import com.plub.domain.repository.SearchRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetSearchPlubRecruitUseCase @Inject constructor(
    private val searchRepository: SearchRepository
) : UseCase<SearchPlubRecruitRequestVo, Flow<UiState<PlubCardListVo>>>() {
    override suspend fun invoke(request: SearchPlubRecruitRequestVo): Flow<UiState<PlubCardListVo>> {
        return searchRepository.searchPlubRecruit(request)
    }
}