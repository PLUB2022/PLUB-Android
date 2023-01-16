package com.plub.data.dto.hobby

import com.google.gson.annotations.SerializedName
import com.plub.data.base.DataDto

data class SubHobbyResponse(
    @SerializedName("id")
    val id: Int = -1,
    @SerializedName("name")
    val name: String = "",
    @SerializedName("parentId")
    val parentId: Int = -1,
    @SerializedName("categoryName")
    val categoryName: String = ""
):DataDto