package com.plub.data.mapper.recruitdetailmapper.host

import com.plub.data.base.Mapper
import com.plub.data.dto.recruitdetail.host.Accounts
import com.plub.domain.model.vo.home.recruitdetailvo.host.AccountsVo

object HostAccountsMapper : Mapper.ResponseMapper<Accounts, AccountsVo> {
    override fun mapDtoToModel(type: Accounts?): AccountsVo {
        return type?.run {
            AccountsVo(
                accountName = this.accountName,
                profileImage = this.profileImage,
                createdAt = this.createdAt,
                answers = this.answers.map {
                    HostAnswersMapper.mapDtoToModel(it)
                }
            )
        }?: AccountsVo("", "", "", emptyList())
    }
}
