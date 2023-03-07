package com.plub.data.mapper

import com.plub.data.base.Mapper
import com.plub.data.dto.board.BoardWriteRequest
import com.plub.domain.model.vo.board.BoardCreateRequestVo

object BoardCreateRequestMapper: Mapper.RequestMapper<BoardWriteRequest, BoardCreateRequestVo> {

    override fun mapModelToDto(type: BoardCreateRequestVo): BoardWriteRequest {
        return type.run {
            BoardWriteRequest(
                feedType = feedType.type,
                title = title,
                content = content,
                feedImage = feedImageUrl
            )
        }
    }
}