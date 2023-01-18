package com.plub.data.mapper.recruitdetailmapper

import com.plub.data.base.Mapper
import com.plub.data.dto.plubJwt.recruitdetail.RecruitDetailJoinedAccountsList
import com.plub.domain.model.vo.home.recruitdetailvo.RecruitDetailJoinedAccountsListVo

object RecruitDetailJoinedAccountsMapper: Mapper.ResponseMapper<RecruitDetailJoinedAccountsList, RecruitDetailJoinedAccountsListVo> {
    override fun mapDtoToModel(type: RecruitDetailJoinedAccountsList?): RecruitDetailJoinedAccountsListVo {
        return type?.run {
            RecruitDetailJoinedAccountsListVo(
                accountId, //profileImage
            )
        }?: RecruitDetailJoinedAccountsListVo(0)
    }
}