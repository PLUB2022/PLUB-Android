package com.plub.domain.model.vo.board

import com.plub.domain.model.DomainModel
import com.plub.domain.model.enums.PlubingFeedType

data class BoardCreateRequestVo(
    val plubingId:Int,
    val feedType: PlubingFeedType,
    val title:String = "",
    val content:String = "",
    val feedImageUrl:String = "",
): DomainModel