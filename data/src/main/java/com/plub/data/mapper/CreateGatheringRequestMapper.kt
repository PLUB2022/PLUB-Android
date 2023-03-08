package com.plub.data.mapper

import com.plub.data.base.Mapper
import com.plub.data.dto.createGathering.CreateGatheringRequest
import com.plub.domain.model.vo.createGathering.CreateGatheringRequestVo

object CreateGatheringRequestMapper: Mapper.RequestMapper<CreateGatheringRequest, CreateGatheringRequestVo> {
    override fun mapModelToDto(type: CreateGatheringRequestVo): CreateGatheringRequest {
        return type.run {
            CreateGatheringRequest(
                subCategoryIds = subCategoryIds,
                title = title,
                name = name,
                goal = goal,
                introduce = introduce,
                mainImage = mainImage,
                time = time,
                days = days,
                onOff = onOff,
                address = address,
                roadAddress = roadAddress,
                placeName = placeName,
                placePositionX = placePositionX,
                placePositionY = placePositionY,
                maxAccountNum = maxAccountNum,
                questions = questions
            )
        }
    }
}