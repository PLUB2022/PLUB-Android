package com.plub.data.mapper.recruitDetailMapper

import com.plub.data.base.Mapper
import com.plub.data.dto.recruitDetail.RecruitDetailJoinedAccountsDataResponse
import com.plub.domain.model.vo.home.recruitDetailVo.RecruitDetailJoinedAccountsListVo

object RecruitDetailJoinedAccountsMapper: Mapper.ResponseMapper<RecruitDetailJoinedAccountsDataResponse, RecruitDetailJoinedAccountsListVo> {
    override fun mapDtoToModel(type: RecruitDetailJoinedAccountsDataResponse?): RecruitDetailJoinedAccountsListVo {
        return type?.run {
            RecruitDetailJoinedAccountsListVo(
                accountId = this.accountId, profileImage = this.profileImage, nickname = nickname
            )
        }?: RecruitDetailJoinedAccountsListVo()
    }
}