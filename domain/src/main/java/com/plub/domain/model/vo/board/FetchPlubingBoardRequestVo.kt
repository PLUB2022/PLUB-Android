package com.plub.domain.model.vo.board

import com.plub.domain.model.DomainModel
import com.plub.domain.model.enums.SocialLoginType

data class FetchPlubingBoardRequestVo(
    val page:Int = -1,
    val plubbingId:Int = -1
): DomainModel