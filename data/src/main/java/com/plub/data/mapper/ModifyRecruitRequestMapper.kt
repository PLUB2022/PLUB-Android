package com.plub.data.mapper

import com.plub.data.base.Mapper
import com.plub.data.dto.createGathering.CreateGatheringRequest
import com.plub.data.dto.modifyGathering.ModifyRecruitRequest
import com.plub.data.dto.modifyGathering.ModifyRecruitRequestBody
import com.plub.domain.model.vo.createGathering.CreateGatheringRequestVo
import com.plub.domain.model.vo.modifyGathering.ModifyRecruitRequestVo

object ModifyRecruitRequestMapper: Mapper.RequestMapper<ModifyRecruitRequest, ModifyRecruitRequestVo> {

    override fun mapModelToDto(type: ModifyRecruitRequestVo): ModifyRecruitRequest {
        return type.run {
            ModifyRecruitRequest(
                plubbingId = plubbingId,
                body = ModifyRecruitRequestBody(
                    title = title,
                    name = name,
                    goal = goal,
                    introduce = introduce,
                    mainImage = mainImage
                )
            )
        }
    }
}