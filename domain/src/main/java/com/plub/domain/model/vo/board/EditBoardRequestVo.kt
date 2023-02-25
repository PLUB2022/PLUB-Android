package com.plub.domain.model.vo.board

import com.plub.domain.model.DomainModel
import com.plub.domain.model.enums.PlubingFeedType
import com.plub.domain.model.enums.SocialLoginType

data class EditBoardRequestVo(
    val plubingId:Int,
    val feedId:Int,
    val feedType: PlubingFeedType,
    val title:String = "",
    val content:String = "",
    val feedImageUrl:String = "",
): DomainModel