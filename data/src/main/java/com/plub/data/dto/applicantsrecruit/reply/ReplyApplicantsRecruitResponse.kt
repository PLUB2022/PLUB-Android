package com.plub.data.dto.applicantsrecruit.reply

import com.plub.data.base.DataDto

data class ReplyApplicantsRecruitResponse(
    val maxAccountNum : Int = -1,
    val curAccountNum : Int = -1
) : DataDto
