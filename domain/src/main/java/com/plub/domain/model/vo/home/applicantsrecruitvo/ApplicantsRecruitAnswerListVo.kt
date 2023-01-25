package com.plub.domain.model.vo.home.applicantsrecruitvo

import com.plub.domain.base.DomainModel

data class ApplicantsRecruitAnswerListVo(
    val questionId : Int,
    val answer : String
) : DomainModel()