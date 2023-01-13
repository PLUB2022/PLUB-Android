package com.plub.data.dto.createGathering

import com.google.gson.annotations.SerializedName
import com.plub.data.base.DataDto
import com.plub.domain.model.enums.SocialLoginType

data class CreateGatheringResponse(
    @SerializedName("plubbingId")
    val plubbingId: Int
): DataDto