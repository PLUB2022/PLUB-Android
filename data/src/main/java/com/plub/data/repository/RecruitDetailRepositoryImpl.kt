package com.plub.data.repository

import com.plub.data.api.BrowseApi
import com.plub.data.base.BaseRepository
import com.plub.data.mapper.recruitdetailmapper.RecruitDetailResponseMapper
import com.plub.domain.UiState
import com.plub.domain.model.vo.home.recruitdetailvo.RecruitDetailResponseVo
import com.plub.domain.repository.RecruitDetailRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RecruitDetailRepositoryImpl @Inject constructor(private val browseApi: BrowseApi) : RecruitDetailRepository, BaseRepository() {
    override suspend fun getRecruitDetail(request: Int): Flow<UiState<RecruitDetailResponseVo>> {
        return apiLaunch(
            browseApi.fetchRecruitDetail(request), RecruitDetailResponseMapper)
    }
}