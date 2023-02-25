package com.plub.domain.model.vo.board

import com.plub.domain.model.DomainModel
import com.plub.domain.model.enums.SocialLoginType

data class GetCommentListVo(
    val plubbingId:Int = -1,
    val feedId:Int = -1,
    val page:Int = -1
): DomainModel