package com.plub.data.mapper.registerinterestmapper

import com.plub.data.base.Mapper
import com.plub.data.dto.plubJwt.registerinterest.RegisterInterestResponse
import com.plub.domain.model.vo.home.interestregistervo.RegisterInterestResponseVo

object InterestRegisterResponseMapper : Mapper.ResponseMapper<RegisterInterestResponse, RegisterInterestResponseVo>{
    override fun mapDtoToModel(type: RegisterInterestResponse?): RegisterInterestResponseVo {
        return type?.run {
            RegisterInterestResponseVo(
                accountId, subCategories
            )
        }?: RegisterInterestResponseVo("", emptyList())
    }
}