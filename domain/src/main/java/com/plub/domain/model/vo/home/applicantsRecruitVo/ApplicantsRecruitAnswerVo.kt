package com.plub.domain.model.vo.home.applicantsRecruitVo

import com.plub.domain.model.DomainModel

data class ApplicantsRecruitAnswerVo(
    val questionId : Int,
    val answer : String
) : DomainModel