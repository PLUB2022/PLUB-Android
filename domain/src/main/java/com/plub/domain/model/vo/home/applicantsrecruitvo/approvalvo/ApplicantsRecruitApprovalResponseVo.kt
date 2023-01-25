package com.plub.domain.model.vo.home.applicantsrecruitvo.approvalvo

import com.plub.domain.base.DomainModel

data class ApplicantsRecruitApprovalResponseVo(
    val maxAccountNum : Int,
    val curAccountNum : Int
) : DomainModel()