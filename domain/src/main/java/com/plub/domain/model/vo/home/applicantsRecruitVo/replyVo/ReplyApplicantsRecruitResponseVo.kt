package com.plub.domain.model.vo.home.applicantsRecruitVo.replyVo

import com.plub.domain.model.DomainModel

data class ReplyApplicantsRecruitResponseVo(
    val maxAccountNum : Int = 0,
    val curAccountNum : Int = 0
) : DomainModel