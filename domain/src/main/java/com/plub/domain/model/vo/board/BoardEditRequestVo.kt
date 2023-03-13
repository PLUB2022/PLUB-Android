package com.plub.domain.model.vo.board

import com.plub.domain.model.DomainModel
import com.plub.domain.model.enums.PlubingFeedType

data class BoardEditRequestVo(
    val plubingId:Int,
    val feedId:Int,
    val feedType: PlubingFeedType,
    val title:String = "",
    val content:String = "",
    val feedImageUrl:String = "",
): DomainModel