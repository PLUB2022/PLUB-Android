package com.plub.domain.model.vo.home.applicantsrecruitvo.replyvo

import com.plub.domain.base.DomainModel

data class ReplyApplicantsRecruitResponseVo(
    val maxAccountNum : Int,
    val curAccountNum : Int
) : DomainModel()