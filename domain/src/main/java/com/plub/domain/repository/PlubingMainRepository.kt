package com.plub.domain.repository

import com.plub.domain.UiState
import com.plub.domain.model.vo.common.HobbyVo
import com.plub.domain.model.vo.home.search.RecentSearchVo
import com.plub.domain.model.vo.plub.PlubCardListVo
import com.plub.domain.model.vo.plub.PlubingMainVo
import com.plub.domain.model.vo.search.SearchPlubRecruitRequestVo
import kotlinx.coroutines.flow.Flow

interface PlubingMainRepository {
    suspend fun getPlubingMain(id:Int): Flow<UiState<PlubingMainVo>>
}