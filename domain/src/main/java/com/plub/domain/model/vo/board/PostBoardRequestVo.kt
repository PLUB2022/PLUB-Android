package com.plub.domain.model.vo.board

import com.plub.domain.model.DomainModel
import com.plub.domain.model.enums.PlubingFeedType
import com.plub.domain.model.enums.SocialLoginType

data class PostBoardRequestVo(
    val plubingId:Int,
    val feedType: PlubingFeedType,
    val title:String = "",
    val content:String = "",
    val feedImageUrl:String = "",
): DomainModel