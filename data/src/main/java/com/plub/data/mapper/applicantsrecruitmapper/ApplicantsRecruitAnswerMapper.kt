package com.plub.data.mapper.applicantsrecruitmapper

import com.plub.data.base.Mapper
import com.plub.data.dto.applicantsrecruit.ApplicantsRecruitRequestAnswerList
import com.plub.domain.model.vo.home.applicantsrecruitvo.ApplicantsRecruitAnswerListVo

object ApplicantsRecruitAnswerMapper : Mapper.RequestMapper<ApplicantsRecruitRequestAnswerList, ApplicantsRecruitAnswerListVo> {
    override fun mapModelToDto(type: ApplicantsRecruitAnswerListVo): ApplicantsRecruitRequestAnswerList {
        return type?.run {
            ApplicantsRecruitRequestAnswerList(
                questionId = this.questionId,
                answer = this.answer
            )
        } ?: ApplicantsRecruitRequestAnswerList()
    }

}