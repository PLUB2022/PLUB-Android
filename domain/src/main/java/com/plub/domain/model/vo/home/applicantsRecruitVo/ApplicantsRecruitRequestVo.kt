package com.plub.domain.model.vo.home.applicantsrecruitvo

import com.plub.domain.model.DomainModel
import com.plub.domain.model.vo.home.applicantsrecruitvo.ApplicantsRecruitAnswerListVo

data class ApplicantsRecruitRequestVo(
    val plubbingId : Int,
    val answers : List<ApplicantsRecruitAnswerListVo>
) : DomainModel