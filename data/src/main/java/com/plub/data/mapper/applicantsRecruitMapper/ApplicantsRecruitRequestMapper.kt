package com.plub.data.mapper.applicantsRecruitMapper

import com.plub.data.base.Mapper
import com.plub.data.dto.applicantsRecruit.ApplicantsRecruitRequest
import com.plub.domain.model.vo.home.applicantsRecruitVo.ApplicantsRecruitRequestVo

object ApplicantsRecruitRequestMapper : Mapper.RequestMapper<ApplicantsRecruitRequest, ApplicantsRecruitRequestVo>{
    override fun mapModelToDto(type: ApplicantsRecruitRequestVo): ApplicantsRecruitRequest {
        return type.run {
            ApplicantsRecruitRequest(
                answers = this.answers.map {
                    ApplicantsRecruitAnswerMapper.mapModelToDto(it)
                }
            )
        } ?: ApplicantsRecruitRequest()
    }
}