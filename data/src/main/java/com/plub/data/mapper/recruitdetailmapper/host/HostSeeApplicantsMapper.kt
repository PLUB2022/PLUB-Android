package com.plub.data.mapper.recruitdetailmapper.host

import com.plub.data.base.Mapper
import com.plub.data.dto.recruitdetail.host.HostApplicantsListResponse
import com.plub.domain.model.vo.home.recruitdetailvo.host.HostApplicantsResponseVo

object HostSeeApplicantsMapper : Mapper.ResponseMapper<HostApplicantsListResponse, HostApplicantsResponseVo> {
    override fun mapDtoToModel(type: HostApplicantsListResponse?): HostApplicantsResponseVo {
        return type?.run {
            HostApplicantsResponseVo(
                appliedAccounts = this.appliedAccounts.map {
                    HostAccountsMapper.mapDtoToModel(it)
                }
            )
        } ?: HostApplicantsResponseVo()
    }
}
