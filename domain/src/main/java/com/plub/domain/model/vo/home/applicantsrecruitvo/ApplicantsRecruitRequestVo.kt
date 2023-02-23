package com.plub.domain.model.vo.home.applicantsRecruitVo

import com.plub.domain.model.DomainModel

data class ApplicantsRecruitRequestVo(
    val plubbingId : Int,
    val answers : List<ApplicantsRecruitAnswerListVo>
) : DomainModel