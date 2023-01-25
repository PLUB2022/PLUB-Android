package com.plub.data.mapper

import com.plub.data.base.Mapper
import com.plub.data.dto.bookmarks.BookmarkResponse
import com.plub.domain.model.vo.home.bookmarkvo.BookmarkResponseVo

object BookmarkMapper : Mapper.ResponseMapper<BookmarkResponse, BookmarkResponseVo>{
    override fun mapDtoToModel(type: BookmarkResponse?): BookmarkResponseVo {
        return type?.run {
            BookmarkResponseVo(
                plubbingId, isBookmarked
            )
        }?: BookmarkResponseVo(0, false)
    }
}