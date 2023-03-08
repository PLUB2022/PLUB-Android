package com.plub.data.repository

import com.plub.data.api.RecruitApi
import com.plub.data.base.BaseRepository
import com.plub.data.mapper.recruitdetailmapper.host.HostSeeApplicantsMapper
import com.plub.domain.UiState
import com.plub.domain.model.vo.home.recruitdetailvo.host.HostApplicantsResponseVo
import com.plub.domain.repository.ApplicantsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ApplicantsRepositoryImpl @Inject constructor(private val recruitApi: RecruitApi) : ApplicantsRepository, BaseRepository() {
    override suspend fun seeApplicants(request: Int): Flow<UiState<HostApplicantsResponseVo>> {
        return apiLaunch(recruitApi.seeApplicants(request), HostSeeApplicantsMapper)
    }
}