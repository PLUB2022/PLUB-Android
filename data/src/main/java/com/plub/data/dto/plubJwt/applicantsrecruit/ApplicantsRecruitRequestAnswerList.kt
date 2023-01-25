package com.plub.data.dto.plubJwt.applicantsrecruit

import com.plub.data.base.DataDto

data class ApplicantsRecruitRequestAnswerList(
    val questionId : Int,
    val answer : String
) : DataDto