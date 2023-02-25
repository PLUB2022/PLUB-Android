package com.plub.data.mapper

import com.plub.data.base.Mapper
import com.plub.data.dto.board.PostBoardRequest
import com.plub.domain.model.vo.board.EditBoardRequestVo

object EditBoardRequestMapper: Mapper.RequestMapper<PostBoardRequest, EditBoardRequestVo> {

    override fun mapModelToDto(type: EditBoardRequestVo): PostBoardRequest {
        return type.run {
            PostBoardRequest(
                feedType = feedType.type,
                title = title,
                content = content,
                feedImage = feedImageUrl
            )
        }
    }
}