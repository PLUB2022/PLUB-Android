package com.plub.data.mapper.applicantsrecruitmapper

import com.plub.data.base.Mapper
import com.plub.data.dto.applicantsrecruit.ApplicantsRecruitRequestAnswerListRequest
import com.plub.domain.model.vo.home.applicantsrecruitvo.ApplicantsRecruitAnswerListVo

object ApplicantsRecruitAnswerMapper : Mapper.RequestMapper<ApplicantsRecruitRequestAnswerListRequest, ApplicantsRecruitAnswerListVo> {
    override fun mapModelToDto(type: ApplicantsRecruitAnswerListVo): ApplicantsRecruitRequestAnswerListRequest {
        return type?.run {
            ApplicantsRecruitRequestAnswerListRequest(
                questionId = this.questionId,
                answer = this.answer
            )
        } ?: ApplicantsRecruitRequestAnswerListRequest()
    }

}