package com.plub.data.mapper

import com.plub.data.base.Mapper
import com.plub.data.dto.bookmark.PlubBookmarkResponse
import com.plub.data.dto.signUp.NicknameCheckResponse
import com.plub.domain.model.vo.bookmark.PlubBookmarkResponseVo

object PlubBookmarkResponseMapper : Mapper.ResponseMapper<PlubBookmarkResponse, PlubBookmarkResponseVo> {

    override fun mapDtoToModel(type: PlubBookmarkResponse?): PlubBookmarkResponseVo {
        return type?.run {
            PlubBookmarkResponseVo(plubbingId, isBookmarked)
        } ?: PlubBookmarkResponseVo()
    }
}