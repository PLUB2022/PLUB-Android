package com.plub.domain.usecase

import com.plub.domain.UiState
import com.plub.domain.base.UseCase
import com.plub.domain.model.vo.home.HomePostRequestVo
import com.plub.domain.model.vo.home.HomePostResponseVo
import com.plub.domain.model.vo.home.SampleCategory
import com.plub.domain.repository.HomePostRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TestPostHomeUseCase @Inject constructor(
    private val homePostRepository: HomePostRepository
) : UseCase<HomePostRequestVo, Flow<UiState<HomePostResponseVo>>>(){
    override fun invoke(request: HomePostRequestVo): Flow<UiState<HomePostResponseVo>> {
        return homePostRepository.trySampleData(request)
    }
}