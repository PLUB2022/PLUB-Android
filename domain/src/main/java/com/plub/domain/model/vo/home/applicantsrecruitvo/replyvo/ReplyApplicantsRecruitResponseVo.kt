package com.plub.domain.model.vo.home.applicantsrecruitvo.replyvo

import com.plub.domain.base.DomainModel

data class ReplyApplicantsRecruitResponseVo(
    val maxAccountNum : Int = 0,
    val curAccountNum : Int = 0
) : DomainModel()