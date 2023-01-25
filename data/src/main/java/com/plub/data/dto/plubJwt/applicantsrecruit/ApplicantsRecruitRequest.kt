package com.plub.data.dto.plubJwt.applicantsrecruit

import com.plub.data.base.DataDto

data class ApplicantsRecruitRequest(
    val answers : List<ApplicantsRecruitRequestAnswerList>
) : DataDto