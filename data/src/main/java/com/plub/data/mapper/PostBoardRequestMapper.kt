package com.plub.data.mapper

import com.plub.data.base.Mapper
import com.plub.data.dto.board.PostBoardRequest
import com.plub.domain.model.vo.board.PostBoardRequestVo

object PostBoardRequestMapper: Mapper.RequestMapper<PostBoardRequest, PostBoardRequestVo> {

    override fun mapModelToDto(type: PostBoardRequestVo): PostBoardRequest {
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