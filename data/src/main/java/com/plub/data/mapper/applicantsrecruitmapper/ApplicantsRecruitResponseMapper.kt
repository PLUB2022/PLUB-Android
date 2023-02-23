package com.plub.data.mapper.applicantsRecruitMapper

import com.plub.data.base.Mapper
import com.plub.data.dto.applicantsRecruit.ApplicantsRecruitResponse
import com.plub.domain.model.vo.home.applicantsRecruitVo.ApplicantsRecruitResponseVo

object ApplicantsRecruitResponseMapper : Mapper.ResponseMapper<ApplicantsRecruitResponse, ApplicantsRecruitResponseVo> {
    override fun mapDtoToModel(type: ApplicantsRecruitResponse?): ApplicantsRecruitResponseVo {
        return type?.run {
            ApplicantsRecruitResponseVo(
                plubbingId = this.plubbingId
            )
        }?: ApplicantsRecruitResponseVo()
    }
}