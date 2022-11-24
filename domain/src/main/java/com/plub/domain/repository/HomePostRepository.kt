package com.plub.domain.repository

import com.plub.domain.UiState
import com.plub.domain.model.vo.home.HomePostRequestVo
import com.plub.domain.model.vo.home.HomePostResponseVo
import com.plub.domain.model.vo.home.SampleCategory
import kotlinx.coroutines.flow.Flow

interface HomePostRepository {
    fun trySampleData(request: HomePostRequestVo) : Flow<UiState<HomePostResponseVo>>
}