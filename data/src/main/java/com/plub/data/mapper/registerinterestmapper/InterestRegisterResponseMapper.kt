package com.plub.data.mapper.registerinterestmapper

import com.plub.data.base.Mapper
import com.plub.data.dto.registerhobbies.RegisterHobbiesResponse
import com.plub.domain.model.vo.home.interestregistervo.RegisterInterestResponseVo

object InterestRegisterResponseMapper : Mapper.ResponseMapper<RegisterHobbiesResponse, RegisterInterestResponseVo>{
    override fun mapDtoToModel(type: RegisterHobbiesResponse?): RegisterInterestResponseVo {
        return type?.run {
            RegisterInterestResponseVo(
                accountId = this.accountId,
                subCategories = this.subCategories
            )
        }?: RegisterInterestResponseVo()
    }
}