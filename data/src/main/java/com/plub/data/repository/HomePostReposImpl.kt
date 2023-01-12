package com.plub.data.repository

import com.plub.data.api.PostHomeApi
import com.plub.data.base.BaseRepository
import com.plub.data.mapper.HomePostMapper
import com.plub.data.mapper.HomePostResponseMapper
import com.plub.domain.UiState
import com.plub.domain.model.vo.home.HomePostRequestVo
import com.plub.domain.model.vo.home.HomePostResponseVo
import com.plub.domain.repository.HomePostRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class HomePostReposImpl @Inject constructor(private val postHomeApi : PostHomeApi) : HomePostRepository, BaseRepository() {
    override suspend fun trySampleData(request: HomePostRequestVo): Flow<UiState<HomePostResponseVo>> {
        val requestDto = HomePostMapper.mapperToSampleRequest(request)
        return apiLaunch(postHomeApi.postHome(requestDto), HomePostResponseMapper)

    }
}