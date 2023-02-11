package com.plub.domain.repository

import com.plub.domain.UiState
import com.plub.domain.model.vo.home.interestregistervo.RegisterInterestResponseVo
import kotlinx.coroutines.flow.Flow

interface RegisterHobbiesRepository {
    suspend fun registerInterest(request : List<Int>) : Flow<UiState<RegisterInterestResponseVo>>
    suspend fun browseInterest() : Flow<UiState<RegisterInterestResponseVo>>
}