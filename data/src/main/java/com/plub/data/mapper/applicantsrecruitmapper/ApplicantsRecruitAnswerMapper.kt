package com.plub.data.mapper.applicantsrecruitmapper

import com.plub.data.base.Mapper
import com.plub.data.dto.plubJwt.applicantsrecruit.ApplicantsRecruitRequestAnswerList
import com.plub.domain.model.vo.home.applicantsrecruitvo.ApplicantsRecruitAnswerListVo

object ApplicantsRecruitAnswerMapper : Mapper.RequestMapper<ApplicantsRecruitRequestAnswerList, ApplicantsRecruitAnswerListVo> {
    override fun mapModelToDto(type: ApplicantsRecruitAnswerListVo): ApplicantsRecruitRequestAnswerList {
        return type?.run {
            ApplicantsRecruitRequestAnswerList(
                questionId, answer
            )
        } ?:ApplicantsRecruitRequestAnswerList(0, "")
    }

}