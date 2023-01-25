package com.plub.data.dto.bookmarks

import com.plub.data.base.DataDto

data class BookmarkResponse(
    val plubbingId : Int,
    val isBookmarked : Boolean
) : DataDto