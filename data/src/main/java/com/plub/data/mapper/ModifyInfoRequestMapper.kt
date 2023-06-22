package com.plub.data.mapper

import com.plub.data.base.Mapper
import com.plub.data.dto.createGathering.CreateGatheringRequest
import com.plub.data.dto.modifyGathering.ModifyInfoRequest
import com.plub.data.dto.modifyGathering.ModifyInfoRequestBody
import com.plub.data.dto.modifyGathering.ModifyRecruitRequest
import com.plub.data.dto.modifyGathering.ModifyRecruitRequestBody
import com.plub.domain.model.vo.createGathering.CreateGatheringRequestVo
import com.plub.domain.model.vo.modifyGathering.ModifyInfoRequestVo
import com.plub.domain.model.vo.modifyGathering.ModifyRecruitRequestVo

object ModifyInfoRequestMapper: Mapper.RequestMapper<ModifyInfoRequest, ModifyInfoRequestVo> {

    override fun mapModelToDto(type: ModifyInfoRequestVo): ModifyInfoRequest {
        return type.run {
            ModifyInfoRequest(
                plubbingId = plubbingId,
                body = ModifyInfoRequestBody(
                    days = days,
                    onOff = onOff,
                    maxAccountNum = maxAccountNum,
                    address = address,
                    roadAddress = roadAddress,
                    placeName = placeName,
                    placePositionX = placePositionX,
                    placePositionY = placePositionY,
                    time = time
                )
            )
        }
    }
}