package com.plub.data.mapper

import com.plub.data.base.Mapper
import com.plub.data.dto.board.PlubingBoardResponse
import com.plub.domain.model.enums.PlubingBoardType
import com.plub.domain.model.enums.PlubingFeedType
import com.plub.domain.model.vo.board.PlubingBoardVo

object PlubingBoardResponseMapper : Mapper.ResponseMapper<PlubingBoardResponse, PlubingBoardVo> {

    override fun mapDtoToModel(type: PlubingBoardResponse?): PlubingBoardVo {
        return type?.run {
            PlubingBoardVo(
                viewType = PlubingBoardType.typeOf(viewType),
                feedType = PlubingFeedType.typeOf(feedType),
                feedId = feedId,
                title = title,
                feedImage = feedImage ?: "",
                content = content,
                createAt = createdAt,
                pin = pin,
                profileImage = profileImage ?: "",
                nickname = nickname,
                likeCount = likeCount,
                commentCount = commentCount,
                isAuthor = isAuthor,
                isHost = isHost
            )
        } ?: PlubingBoardVo()
    }
}