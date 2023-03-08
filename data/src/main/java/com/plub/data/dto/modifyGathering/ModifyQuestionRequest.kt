package com.plub.data.dto.modifyGathering

import com.google.gson.annotations.SerializedName
import com.plub.data.base.DataDto

data class ModifyQuestionRequest(
    val plubbingId: Int,
    val body: ModifyQuestionRequestBody
): DataDto

data class ModifyQuestionRequestBody(
    @SerializedName("questions")
    val questions: List<String>
): DataDto