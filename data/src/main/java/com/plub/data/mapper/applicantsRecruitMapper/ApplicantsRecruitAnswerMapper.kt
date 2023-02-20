package com.plub.data.mapper.applicantsRecruitMapper

import com.plub.data.base.Mapper
import com.plub.data.dto.applicantsRecruit.ApplicantsRecruitRequestAnswerListRequest
import com.plub.domain.model.vo.home.applicantsRecruitVo.ApplicantsRecruitAnswerListVo

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