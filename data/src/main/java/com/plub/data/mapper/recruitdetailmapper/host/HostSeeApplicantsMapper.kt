package com.plub.data.mapper.recruitdetailmapper.host

import com.plub.data.base.Mapper
import com.plub.data.dto.plubJwt.recruitdetail.host.HostApplicantsResponse
import com.plub.domain.model.vo.home.applicantsrecruitvo.ApplicantsRecruitResponseVo
import com.plub.domain.model.vo.home.recruitdetailvo.host.HostApplicantsResponseVo

object HostSeeApplicantsMapper : Mapper.ResponseMapper<HostApplicantsResponse, HostApplicantsResponseVo> {
    override fun mapDtoToModel(type: HostApplicantsResponse?): HostApplicantsResponseVo {
        return type?.run {
            HostApplicantsResponseVo(
                appliedAccounts.map {
                    HostAccountsMapper.mapDtoToModel(it)
                }
            )
        }?: HostApplicantsResponseVo(emptyList())
    }
}