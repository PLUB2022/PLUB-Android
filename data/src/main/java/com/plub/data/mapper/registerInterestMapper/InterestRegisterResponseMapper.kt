package com.plub.data.mapper.registerInterestMapper

import com.plub.data.base.Mapper
import com.plub.data.dto.registerHobbies.RegisterHobbiesResponse
import com.plub.domain.model.vo.home.interestRegisterVo.RegisterInterestResponseVo

object InterestRegisterResponseMapper : Mapper.ResponseMapper<RegisterHobbiesResponse, RegisterInterestResponseVo>{
    override fun mapDtoToModel(type: RegisterHobbiesResponse?): RegisterInterestResponseVo {
        return type?.run {
            RegisterInterestResponseVo(
                subCategories = this.subCategories
            )
        }?: RegisterInterestResponseVo()
    }
}