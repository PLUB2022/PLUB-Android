package com.plub.domain.model.vo.home.applicantsrecruitvo

import com.plub.domain.base.DomainModel

data class ApplicantsRecruitRequestVo(
    val plubbingId : Int,
    val answers : List<ApplicantsRecruitAnswerListVo>
) : DomainModel()
