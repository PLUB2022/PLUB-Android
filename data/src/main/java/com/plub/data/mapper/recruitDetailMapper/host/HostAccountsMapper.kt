package com.plub.data.mapper.recruitDetailMapper.host

import com.plub.data.base.Mapper
import com.plub.data.dto.recruitDetail.host.AccountDataResponse
import com.plub.domain.model.vo.home.recruitDetailVo.host.AccountsVo

object HostAccountsMapper : Mapper.ResponseMapper<AccountDataResponse, AccountsVo> {
    override fun mapDtoToModel(type: AccountDataResponse?): AccountsVo {
        return type?.run {
            AccountsVo(
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
