package com.plub.data.mapper

import com.plub.data.base.Mapper
import com.plub.data.dto.board.PlubingBoardListResponse
import com.plub.domain.model.vo.board.PlubingBoardListVo

object PlubingBoardListResponseMapper : Mapper.ResponseMapper<PlubingBoardListResponse, PlubingBoardListVo> {

    override fun mapDtoToModel(type: PlubingBoardListResponse?): PlubingBoardListVo {
        return type?.run {
            PlubingBoardListVo(
                totalPages = totalPages,
                totalElements = totalElements,
                content = content.map {
                    PlubingBoardResponseMapper.mapDtoToModel(it)
                },
                last = last
            )
        }?: PlubingBoardListVo()
    }
}