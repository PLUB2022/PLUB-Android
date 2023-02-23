package com.plub.data.mapper.recruitDetailMapper.host

import com.plub.data.base.Mapper
import com.plub.data.dto.recruitDetail.host.EndRecruitResponse
import com.plub.domain.model.vo.home.applicantsRecruitVo.ApplicantsRecruitResponseVo

object HostRecruitEndMapper : Mapper.ResponseMapper<EndRecruitResponse, ApplicantsRecruitResponseVo> {
    override fun mapDtoToModel(type: EndRecruitResponse?): ApplicantsRecruitResponseVo {
        return type?.run {
            ApplicantsRecruitResponseVo(
                plubbingId = this.plubbingId
            )
        }?: ApplicantsRecruitResponseVo()
    }
}