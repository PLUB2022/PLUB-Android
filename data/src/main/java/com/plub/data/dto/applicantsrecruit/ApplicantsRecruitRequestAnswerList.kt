package com.plub.data.dto.applicantsrecruit

import com.plub.data.base.DataDto

data class ApplicantsRecruitRequestAnswerList(
    val questionId : Int = 0,
    val answer : String = ""
) : DataDto