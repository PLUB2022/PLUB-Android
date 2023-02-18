package com.plub.data.dto.modifyGathering

import com.google.gson.annotations.SerializedName
import com.plub.data.base.DataDto
import com.plub.domain.model.enums.SocialLoginType

data class ModifyRecruitRequest(
    @SerializedName("title")
    val title:String,
    @SerializedName("name")
    val name: String,
    @SerializedName("goal")
    val goal: String,
    @SerializedName("introduce")
    val introduce: String,
    @SerializedName("mainImage")
    val mainImage: String,
): DataDto