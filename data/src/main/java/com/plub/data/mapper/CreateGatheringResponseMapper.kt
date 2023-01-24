package com.plub.data.mapper

import com.google.gson.annotations.SerializedName
import com.plub.data.base.Mapper
import com.plub.data.dto.createGathering.CreateGatheringRequest
import com.plub.data.dto.createGathering.CreateGatheringResponse
import com.plub.data.dto.signUp.SignUpRequest
import com.plub.data.util.DateFormatUtil
import com.plub.domain.model.enums.Gender
import com.plub.domain.model.vo.createGathering.CreateGatheringRequestVo
import com.plub.domain.model.vo.createGathering.CreateGatheringResponseVo
import com.plub.domain.model.vo.signUp.SignUpRequestVo
import java.util.*

object CreateGatheringResponseMapper: Mapper.ResponseMapper<CreateGatheringResponse, CreateGatheringResponseVo> {
    override fun mapDtoToModel(type: CreateGatheringResponse?): CreateGatheringResponseVo {
        return type?.run {
            CreateGatheringResponseVo(
                plubbingId = plubbingId
            )
        } ?: CreateGatheringResponseVo()
    }
}