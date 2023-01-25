package com.plub.data.dto.applicantsrecruit

import com.plub.data.base.DataDto

data class ApplicantsRecruitRequestAnswerList(
    val questionId : Int,
    val answer : String
) : DataDto