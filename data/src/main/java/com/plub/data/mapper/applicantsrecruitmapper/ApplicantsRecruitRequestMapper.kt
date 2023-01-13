package com.plub.data.mapper.applicantsrecruitmapper

import com.plub.data.base.Mapper
import com.plub.data.base.Mapper.RequestMapper
import com.plub.data.dto.plubJwt.applicantsrecruit.ApplicantsRecruitRequest
import com.plub.data.dto.signUp.SignUpRequest
import com.plub.data.util.DateFormatUtil
import com.plub.domain.model.enums.Gender
import com.plub.domain.model.vo.home.applicantsrecruitvo.ApplicantsRecruitRequestVo
import com.plub.domain.model.vo.signUp.SignUpRequestVo
import java.util.*

object ApplicantsRecruitRequestMapper : RequestMapper<ApplicantsRecruitRequest, ApplicantsRecruitRequestVo>{
    override fun mapModelToDto(type: ApplicantsRecruitRequestVo): ApplicantsRecruitRequest {
        return type.run {
            ApplicantsRecruitRequest(
                plubbingId,
                accessToken,
                answers.map {
                    ApplicantsRecruitAnswerMapper.mapModelToDto(it)
                }
            )
        } ?:ApplicantsRecruitRequest(0, "",emptyList())
    }
}