package com.plub.domain.model.vo.board

import com.plub.domain.model.DomainModel
import com.plub.domain.model.enums.SocialLoginType

data class BoardRequestVo(
    val plubbingId:Int = -1,
    val feedId:Int = -1,
    val commentId:Int = -1
): DomainModel