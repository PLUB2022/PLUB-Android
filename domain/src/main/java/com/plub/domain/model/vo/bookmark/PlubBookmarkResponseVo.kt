package com.plub.domain.model.vo.bookmark

import com.plub.domain.base.DomainModel

data class PlubBookmarkResponseVo(
    val id:Int = -1,
    val isBookmarked:Boolean = false
): DomainModel
