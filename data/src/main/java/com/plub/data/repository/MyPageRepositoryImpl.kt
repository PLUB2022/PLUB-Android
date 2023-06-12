package com.plub.data.repository

import com.plub.data.api.MyPageApi
import com.plub.data.base.BaseRepository
import com.plub.data.mapper.myPageMapper.MyBoardMapper
import com.plub.data.mapper.myPageMapper.MyGatheringMapper
import com.plub.data.mapper.myPageMapper.MyPageMyApplicationMapper
import com.plub.data.mapper.myPageMapper.MyToDoWithTileMapper
import com.plub.domain.UiState
import com.plub.domain.error.FeedError
import com.plub.domain.error.TodoError
import com.plub.domain.model.enums.MyPageGatheringStateType
import com.plub.domain.model.vo.board.PlubingBoardListVo
import com.plub.domain.model.vo.myPage.MyPageActiveRequestVo
import com.plub.domain.model.vo.myPage.MyPageGatheringVo
import com.plub.domain.model.vo.myPage.MyPageMyApplicationVo
import com.plub.domain.model.vo.myPage.MyPageToDoWithTitleVo
import com.plub.domain.repository.MyPageRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MyPageRepositoryImpl @Inject constructor(private val myPageApi: MyPageApi) : MyPageRepository, BaseRepository() {

    override suspend fun getMyGathering(request: MyPageGatheringStateType): Flow<UiState<MyPageGatheringVo>> {
        return apiLaunch(apiCall = { myPageApi.getMyGathering(request.type) }, MyGatheringMapper)
    }

    override suspend fun getMyApplicationWithPlubInfo(request: Int): Flow<UiState<MyPageMyApplicationVo>> {
        return apiLaunch(apiCall = { myPageApi.getMyApplication(request) }, MyPageMyApplicationMapper)
    }

    override suspend fun getMyToDo(request: MyPageActiveRequestVo): Flow<UiState<MyPageToDoWithTitleVo>> {
        return apiLaunch(apiCall = { myPageApi.getMyToDo(request.plubbingId, request.cursorId) }, MyToDoWithTileMapper){
            TodoError.make(it)
        }
    }

    override suspend fun getMyPost(request: MyPageActiveRequestVo): Flow<UiState<PlubingBoardListVo>> {
        return apiLaunch(apiCall = { myPageApi.getMyPost(request.plubbingId, request.cursorId) }, MyBoardMapper){
            FeedError.make(it)
        }
    }
}