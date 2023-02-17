package com.plub.data.mapper

import com.plub.data.base.DataDto
import com.plub.data.base.Mapper
import com.plub.data.dto.board.PlubingBoardListResponse
import com.plub.data.dto.board.PlubingPinListResponse
import com.plub.domain.model.vo.board.PlubingBoardListVo
import com.plub.domain.model.vo.board.PlubingBoardVo

object UnitResponseMapper : Mapper.ResponseMapper<DataDto.DTO, Unit> {
    override fun mapDtoToModel(type: DataDto.DTO?) {

    }
}