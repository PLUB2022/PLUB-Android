package com.plub.domain.model.vo.home.applicantsRecruitVo

import com.plub.domain.model.DomainModel
import com.plub.domain.model.vo.home.applicantsRecruitVo.ApplicantsRecruitAnswerVo

data class ApplicantsRecruitRequestVo(
    val plubbingId : Int,
    val answers : List<ApplicantsRecruitAnswerVo>
) : DomainModel