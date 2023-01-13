package com.plub.data.mapper.applicantsrecruitmapper

import com.plub.data.base.Mapper
import com.plub.data.dto.plubJwt.applicantsrecruit.ApplicantsRecruitRequest
import com.plub.domain.model.vo.home.applicantsrecruitvo.ApplicantsRecruitRequestVo

object ApplicantsRecruitRequestMapper : Mapper.RequestMapper<ApplicantsRecruitRequest, ApplicantsRecruitRequestVo>{
    override fun mapModelToDto(type: ApplicantsRecruitRequestVo): ApplicantsRecruitRequest {
        return type.run {
            ApplicantsRecruitRequest(
                answers.map {
                    ApplicantsRecruitAnswerMapper.mapModelToDto(it)
                }
            )
        } ?:ApplicantsRecruitRequest(emptyList())
    }
}