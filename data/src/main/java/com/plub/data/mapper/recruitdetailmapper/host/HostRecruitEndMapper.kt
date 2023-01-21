package com.plub.data.mapper.recruitdetailmapper.host

import com.plub.data.base.Mapper
import com.plub.data.dto.plubJwt.recruitdetail.host.EndRecruitResponse
import com.plub.domain.model.vo.home.applicantsrecruitvo.ApplicantsRecruitResponseVo

object HostRecruitEndMapper : Mapper.ResponseMapper<EndRecruitResponse, ApplicantsRecruitResponseVo> {
    override fun mapDtoToModel(type: EndRecruitResponse?): ApplicantsRecruitResponseVo {
        return type?.run {
            ApplicantsRecruitResponseVo(
                plubbingId
            )
        }?: ApplicantsRecruitResponseVo(0)
    }
}