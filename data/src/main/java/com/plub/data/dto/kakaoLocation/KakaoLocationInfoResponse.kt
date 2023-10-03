package com.plub.data.dto.kakaoLocation

import com.plub.data.base.DataDto
import kotlinx.serialization.SerialName

data class KakaoLocationInfoResponse(
    @SerialName("documents")
    val documents: List<KakaoLocationInfoDocument> = emptyList(),

    @SerialName("meta")
    val meta: Meta = Meta(),
) : DataDto

data class KakaoLocationInfoDocument(
    @SerialName("place_name")
    val placeName: String = "",
)

data class Meta(
    @SerialName("is_end")
    val isEnd: Boolean = false,
    @SerialName("pageable_count")
    val documentTotalCount: Int = 0,
)
