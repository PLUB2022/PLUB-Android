package com.plub.data.repository

import com.plub.data.api.PlubingBoardApi
import com.plub.data.base.BaseRepository
import com.plub.data.mapper.PlubingBoardListResponseMapper
import com.plub.data.mapper.PlubingPinListResponseMapper
import com.plub.domain.UiState
import com.plub.domain.model.vo.board.FetchPlubingBoardRequestVo
import com.plub.domain.model.vo.board.PlubingBoardListVo
import com.plub.domain.model.vo.board.PlubingBoardVo
import com.plub.domain.repository.PlubingBoardRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PlubingBoardRepositoryImpl @Inject constructor(private val boardApi: PlubingBoardApi) : PlubingBoardRepository, BaseRepository() {

    override suspend fun getPlubingBoardList(request: FetchPlubingBoardRequestVo): Flow<UiState<PlubingBoardListVo>> {
        return apiLaunch(boardApi.getBoardList(request.plubbingId, request.page), PlubingBoardListResponseMapper)
    }

    override suspend fun getPlubingPinList(id: Int): Flow<UiState<List<PlubingBoardVo>>> {
        return apiLaunch(boardApi.getPinList(id), PlubingPinListResponseMapper)
    }
}