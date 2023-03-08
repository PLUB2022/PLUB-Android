package com.plub.data.mapper

import com.plub.data.base.Mapper
import com.plub.data.dto.board.BoardWriteRequest
import com.plub.domain.model.vo.board.BoardEditRequestVo

object BoardEditRequestMapper: Mapper.RequestMapper<BoardWriteRequest, BoardEditRequestVo> {

    override fun mapModelToDto(type: BoardEditRequestVo): BoardWriteRequest {
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