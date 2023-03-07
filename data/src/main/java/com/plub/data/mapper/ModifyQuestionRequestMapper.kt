package com.plub.data.mapper

import com.plub.data.base.Mapper
import com.plub.data.dto.modifyGathering.ModifyQuestionRequest
import com.plub.data.dto.modifyGathering.ModifyQuestionRequestBody
import com.plub.data.dto.signUp.SignUpRequest
import com.plub.data.util.DateFormatUtil
import com.plub.domain.model.enums.Gender
import com.plub.domain.model.vo.modifyGathering.ModifyQuestionRequestVo
import com.plub.domain.model.vo.signUp.SignUpRequestVo
import java.util.*

object ModifyQuestionRequestMapper: Mapper.RequestMapper<ModifyQuestionRequest, ModifyQuestionRequestVo> {

    override fun mapModelToDto(type: ModifyQuestionRequestVo): ModifyQuestionRequest {
        return type.run {
            ModifyQuestionRequest(
                plubbingId = plubbingId,
                body = ModifyQuestionRequestBody(questions = questions)
            )
        }
    }
}