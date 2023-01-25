
package com.plub.domain.model.vo.home.bookmarkvo

import com.plub.domain.base.DomainModel

data class BookmarkResponseVo(
    val plubbingId : Int,
    val isBookmarked : Boolean
):DomainModel()