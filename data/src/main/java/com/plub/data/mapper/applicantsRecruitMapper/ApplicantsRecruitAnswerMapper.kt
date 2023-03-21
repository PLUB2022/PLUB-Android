package com.plub.data.mapper.applicantsRecruitMapper

import com.plub.data.base.Mapper
import com.plub.data.dto.applicantsRecruit.ApplicantsRecruitRequestAnswerRequest
import com.plub.domain.model.vo.home.applicantsRecruitVo.ApplicantsRecruitAnswerVo

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