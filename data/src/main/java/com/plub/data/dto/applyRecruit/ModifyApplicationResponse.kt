package com.plub.data.dto.applyRecruit

import com.google.gson.annotations.SerializedName
import com.plub.data.base.DataDto

data class ModifyApplicationResponse(
    @SerializedName("plubbingId")
    val plubbingId : Int = -1
) : DataDto
