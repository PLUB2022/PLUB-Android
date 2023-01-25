package com.plub.data.dto.applicantsrecruit.approval

import com.plub.data.base.DataDto

data class ApprovalApplicantsRecruitResponse(
    val maxAccountNum : Int,
    val curAccountNum : Int
) : DataDto