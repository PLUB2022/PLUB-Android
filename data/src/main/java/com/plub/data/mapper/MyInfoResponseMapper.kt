package com.plub.data.mapper

import com.plub.data.base.Mapper
import com.plub.data.dto.account.MyInfoResponse
import com.plub.domain.model.vo.account.MyInfoResponseVo

object MyInfoResponseMapper : Mapper.ResponseMapper<MyInfoResponse, MyInfoResponseVo> {
    override fun mapDtoToModel(type: MyInfoResponse?): MyInfoResponseVo {
        return type?.run {
            MyInfoResponseVo(
                email = email,
                nickname = nickname,
                socialType = socialType,
                birthday = birthday,
                gender = gender,
                introduce = introduce,
                profileImage = profileImage
            )
        } ?: MyInfoResponseVo()
    }
}