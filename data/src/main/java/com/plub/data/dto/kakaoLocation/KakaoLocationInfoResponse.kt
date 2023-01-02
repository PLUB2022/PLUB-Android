package com.plub.data.dto.kakaoLocation

import com.google.gson.annotations.SerializedName
import com.plub.data.base.DataDto

data class KakaoLocationInfoResponse(
    @SerializedName("documents")
    val documents: List<KakaoLocationInfoDocument>
): DataDto

data class KakaoLocationInfoDocument(
    @SerializedName("place_name")
    val placeName: String,
    @SerializedName("x")
    val placePositionX: String,
    @SerializedName("y")
    val placePositionY: String,
    @SerializedName("road_address_name")
    val roadAddressName: String
)