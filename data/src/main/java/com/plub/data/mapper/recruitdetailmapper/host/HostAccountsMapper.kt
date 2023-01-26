package com.plub.data.mapper.recruitdetailmapper.host

import com.plub.data.base.Mapper
import com.plub.data.dto.recruitdetail.host.Accounts
import com.plub.domain.model.vo.home.recruitdetailvo.host.AccountsVo

object HostAccountsMapper : Mapper.ResponseMapper<Accounts, AccountsVo> {
    override fun mapDtoToModel(type: Accounts?): AccountsVo {
        return type?.run {
            AccountsVo(
                accountName,
                profileImage, createdAt,
                answers.map {
                    HostAnswersMapper.mapDtoToModel(it)
                }
            )
        }?: AccountsVo("", "", "", emptyList())
    }
}
