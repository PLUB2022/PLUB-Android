package com.plub.data.dto.kakaoLocation

import com.google.gson.annotations.SerializedName
import com.plub.data.base.DataDto

data class KakaoLocationInfoResponse(
    @SerializedName("documents")
    val documents: List<KakaoLocationInfoDocument> = emptyList(),

    @SerializedName("meta")
    val meta: Meta = Meta()
): DataDto

data class KakaoLocationInfoDocument(
    @SerializedName("place_name")
    val placeName: String = "",
    @SerializedName("x")
    val placePositionX: String = "",
    @SerializedName("y")
    val placePositionY: String = "",
    @SerializedName("address_name")
    val addressName: String = "",
    @SerializedName("road_address_name")
    val roadAddressName: String = ""
)

data class Meta(
    @SerializedName("is_end")
    val isEnd: Boolean = false,
    @SerializedName("pageable_count")
    val documentTotalCount: Int = 0
)