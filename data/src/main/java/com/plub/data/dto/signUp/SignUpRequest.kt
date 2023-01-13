package com.plub.data.dto.signUp

import com.google.gson.annotations.SerializedName
import com.plub.data.base.DataDto

data class SignUpRequest(
    @SerializedName("signToken")
    val signToken: String,
    @SerializedName("categoryList")
    val categoryList: List<Int>,
    @SerializedName("profileImage")
    val profileImage: String,
    @SerializedName("birthday")
    val birthday: String,
    @SerializedName("gender")
    val gender: String,
    @SerializedName("introduce")
    val introduce: String,
    @SerializedName("nickname")
    val nickname: String,
    @SerializedName("marketPolicy")
    val marketPolicy: Boolean,
    @SerializedName("personalPolicy")
    val personalPolicy: Boolean,
    @SerializedName("placePolicy")
    val placePolicy: Boolean,
    @SerializedName("usePolicy")
    val usePolicy: Boolean,
    @SerializedName("agePolicy")
    val agePolicy: Boolean,
): DataDto