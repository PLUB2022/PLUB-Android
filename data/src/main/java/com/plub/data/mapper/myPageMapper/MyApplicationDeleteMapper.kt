package com.plub.data.mapper.myPageMapper

import com.plub.data.base.Mapper
import com.plub.data.dto.myPage.MyApplicationDeleteResponse

object MyApplicationDeleteMapper : Mapper.ResponseMapper<MyApplicationDeleteResponse, Int> {
    override fun mapDtoToModel(type: MyApplicationDeleteResponse?): Int {
        return type?.run {
            plubbingId
        }?: 0
    }
}