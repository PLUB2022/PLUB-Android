package com.plub.domain.repository

import com.plub.domain.UiState
import com.plub.domain.model.vo.home.recruitdetailvo.RecruitDetailRequestVo
import com.plub.domain.model.vo.home.recruitdetailvo.RecruitDetailResponseVo
import kotlinx.coroutines.flow.Flow

interface RecruitDetailRepository {
    suspend fun getRecruitDetail(request : RecruitDetailRequestVo) : Flow<UiState<RecruitDetailResponseVo>>
}