package com.plub.data.repository

import com.plub.data.api.HobbyApi
import com.plub.data.api.RecruitApi
import com.plub.data.base.BaseRepository
import com.plub.data.mapper.recruitdetailmapper.host.HostRecruitEndMapper
import com.plub.data.mapper.recruitdetailmapper.host.HostSeeApplicantsMapper
import com.plub.domain.UiState
import com.plub.domain.model.vo.home.applicantsrecruitvo.ApplicantsRecruitResponseVo
import com.plub.domain.model.vo.home.recruitdetailvo.host.HostApplicantsResponseVo
import com.plub.domain.repository.HostRecruitRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class HostRecruitReposImpl @Inject constructor(private val recruitApi: RecruitApi) : HostRecruitRepository, BaseRepository() {
    override suspend fun endRecruit(request: Int): Flow<UiState<ApplicantsRecruitResponseVo>> {
        return apiLaunch(recruitApi.endRecruit(request), HostRecruitEndMapper)
    }

    override suspend fun seeApplicants(request: Int): Flow<UiState<HostApplicantsResponseVo>> {
        return apiLaunch(recruitApi.seeApplicants(request), HostSeeApplicantsMapper)
    }
}
