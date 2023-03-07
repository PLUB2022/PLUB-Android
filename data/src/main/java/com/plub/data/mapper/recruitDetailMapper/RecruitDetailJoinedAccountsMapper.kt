package com.plub.data.mapper.recruitdetailmapper

import com.plub.data.base.Mapper
import com.plub.data.dto.recruitdetail.RecruitDetailJoinedAccountsDataResponse
import com.plub.domain.model.vo.home.recruitdetailvo.RecruitDetailJoinedAccountsVo

object RecruitDetailJoinedAccountsMapper: Mapper.ResponseMapper<RecruitDetailJoinedAccountsDataResponse, RecruitDetailJoinedAccountsVo> {
    override fun mapDtoToModel(type: RecruitDetailJoinedAccountsDataResponse?): RecruitDetailJoinedAccountsVo {
        return type?.run {
            RecruitDetailJoinedAccountsVo(
                accountId = this.accountId, profileImage = this.profileImage, nickname = nickname
            )
        }?: RecruitDetailJoinedAccountsVo()
    }
}