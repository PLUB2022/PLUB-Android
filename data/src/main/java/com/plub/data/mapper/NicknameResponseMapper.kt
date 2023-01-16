package com.plub.data.mapper

import com.plub.data.base.Mapper
import com.plub.data.dto.signUp.NicknameCheckResponse

object NicknameResponseMapper: Mapper.ResponseMapper<NicknameCheckResponse, Boolean> {

    override fun mapDtoToModel(type: NicknameCheckResponse?): Boolean {
        return type?.isAvailableNickname ?: false
    }
}