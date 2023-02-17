package com.plub.data.mapper

import com.plub.data.base.Mapper
import com.plub.data.dto.board.PlubingBoardListResponse
import com.plub.data.dto.board.PlubingPinListResponse
import com.plub.domain.model.vo.board.PlubingBoardListVo
import com.plub.domain.model.vo.board.PlubingBoardVo

object PlubingPinListResponseMapper : Mapper.ResponseMapper<PlubingPinListResponse, List<PlubingBoardVo>> {

    override fun mapDtoToModel(type: PlubingPinListResponse?): List<PlubingBoardVo> {
        return type?.pinedFeedList?.map {
            PlubingBoardResponseMapper.mapDtoToModel(it)
        }?: emptyList()
    }
}