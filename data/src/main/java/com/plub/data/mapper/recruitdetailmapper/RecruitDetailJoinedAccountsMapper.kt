package com.plub.data.mapper.recruitdetailmapper

import com.plub.data.base.Mapper
import com.plub.data.dto.recruitdetail.RecruitDetailJoinedAccountsDataResponse
import com.plub.domain.model.vo.home.recruitdetailvo.RecruitDetailJoinedAccountsListVo

object RecruitDetailJoinedAccountsMapper: Mapper.ResponseMapper<RecruitDetailJoinedAccountsDataResponse, RecruitDetailJoinedAccountsListVo> {
    override fun mapDtoToModel(type: RecruitDetailJoinedAccountsDataResponse?): RecruitDetailJoinedAccountsListVo {
        return type?.run {
            RecruitDetailJoinedAccountsListVo(
                accountId = this.accountId, profileImage = this.profileImage
            )
        }?: RecruitDetailJoinedAccountsListVo()
    }
}