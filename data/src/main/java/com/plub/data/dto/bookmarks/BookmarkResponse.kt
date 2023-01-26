package com.plub.data.dto.bookmarks

import com.plub.data.base.DataDto

data class BookmarkResponse(
    val plubbingId : Int = -1,
    val isBookmarked : Boolean = false
) : DataDto