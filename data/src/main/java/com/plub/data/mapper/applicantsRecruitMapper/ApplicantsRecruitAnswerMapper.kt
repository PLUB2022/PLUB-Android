package com.plub.data.mapper.applicantsrecruitmapper

import com.plub.data.base.Mapper
import com.plub.data.dto.applicantsrecruit.ApplicantsRecruitRequestAnswerRequest
import com.plub.domain.model.vo.home.applicantsrecruitvo.ApplicantsRecruitAnswerVo

object ApplicantsRecruitAnswerMapper : Mapper.RequestMapper<ApplicantsRecruitRequestAnswerRequest, ApplicantsRecruitAnswerVo> {
    override fun mapModelToDto(type: ApplicantsRecruitAnswerVo): ApplicantsRecruitRequestAnswerRequest {
        return type?.run {
            ApplicantsRecruitRequestAnswerRequest(
                questionId = this.questionId,
                answer = this.answer
            )
        } ?: ApplicantsRecruitRequestAnswerRequest()
    }

}