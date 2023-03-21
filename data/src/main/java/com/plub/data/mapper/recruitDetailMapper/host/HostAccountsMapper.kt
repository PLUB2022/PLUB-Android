package com.plub.data.mapper.recruitdetailmapper.host

import com.plub.data.base.Mapper
import com.plub.data.dto.recruitDetail.host.AccountDataResponse
import com.plub.domain.model.vo.home.recruitdetailvo.host.AccountsVo

object HostAccountsMapper : Mapper.ResponseMapper<AccountDataResponse, AccountsVo> {
    override fun mapDtoToModel(type: AccountDataResponse?): AccountsVo {
        return type?.run {
            AccountsVo(
                accountId = accountId,
                accountName = this.accountName,
                profileImage = this.profileImage,
                createdAt = this.createdAt,
                answers = this.answers.map {
                    HostAnswersMapper.mapDtoToModel(it)
                }
            )
        }?: AccountsVo()
    }
}
