package com.plub.domain.repository

import com.plub.domain.UiState
import com.plub.domain.model.enums.MyPageGatheringStateType
import com.plub.domain.model.vo.board.PlubingBoardListVo
import com.plub.domain.model.vo.myPage.MyPageActiveRequestVo
import com.plub.domain.model.vo.myPage.MyPageGatheringVo
import com.plub.domain.model.vo.myPage.MyPageMyApplicationVo
import com.plub.domain.model.vo.myPage.MyPageToDoWithTitleVo
import kotlinx.coroutines.flow.Flow

interface MyPageRepository {
    suspend fun getMyGathering(request: MyPageGatheringStateType): Flow<UiState<MyPageGatheringVo>>
    suspend fun getMyApplicationWithPlubInfo(request: Int) : Flow<UiState<MyPageMyApplicationVo>>
    suspend fun getMyToDo(request: MyPageActiveRequestVo) : Flow<UiState<MyPageToDoWithTitleVo>>
    suspend fun getMyPost(request: MyPageActiveRequestVo): Flow<UiState<PlubingBoardListVo>>
}