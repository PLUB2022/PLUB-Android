package com.plub.data.mapper.applicantsrecruitmapper

import com.plub.data.base.Mapper
import com.plub.data.dto.applicantsRecruit.ApplicantsRecruitResponse
import com.plub.domain.model.vo.home.applicantsrecruitvo.ApplicantsRecruitResponseVo

object ApplicantsRecruitResponseMapper : Mapper.ResponseMapper<ApplicantsRecruitResponse, ApplicantsRecruitResponseVo> {
    override fun mapDtoToModel(type: ApplicantsRecruitResponse?): ApplicantsRecruitResponseVo {
        return type?.run {
            ApplicantsRecruitResponseVo(
                plubbingId = this.plubbingId
            )
        }?: ApplicantsRecruitResponseVo()
    }
}