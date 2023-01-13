package com.plub.data.dto.plubJwt.applicantsrecruit.reply

import com.plub.data.base.DataDto

data class ReplyApplicantsRecruitResponse(
    val maxAccountNum : Int,
    val curAccountNum : Int
) : DataDto
