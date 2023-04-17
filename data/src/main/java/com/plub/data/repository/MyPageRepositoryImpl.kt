package com.plub.data.repository

import com.plub.data.api.MyPageApi
import com.plub.data.base.BaseRepository
import com.plub.data.mapper.PlubingMainResponseMapper
import com.plub.data.mapper.myPageMapper.MyGatheringMapper
import com.plub.data.mapper.myPageMapper.MyPageMyApplicationMapper
import com.plub.domain.UiState
import com.plub.domain.model.enums.MyPageGatheringStateType
import com.plub.domain.model.vo.myPage.MyPageGatheringVo
import com.plub.domain.model.vo.myPage.MyPageMyApplicationVo
import com.plub.domain.repository.MyPageRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MyPageRepositoryImpl @Inject constructor(private val myPageApi: MyPageApi) : MyPageRepository, BaseRepository() {

    override suspend fun getMyGathering(request: MyPageGatheringStateType): Flow<UiState<MyPageGatheringVo>> {
        return apiLaunch(myPageApi.getMyGathering(request.type), MyGatheringMapper)
    }

    override suspend fun getMyApplicationWithPlubInfo(request: Int): Flow<UiState<MyPageMyApplicationVo>> {
        return apiLaunch(myPageApi.getMyApplication(request), MyPageMyApplicationMapper)
    }
}