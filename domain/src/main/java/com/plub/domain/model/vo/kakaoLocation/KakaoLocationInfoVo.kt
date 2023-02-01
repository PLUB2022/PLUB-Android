package com.plub.domain.model.vo.kakaoLocation

import com.plub.domain.base.DomainModel

data class KakaoLocationInfoVo(
    val documents: List<KakaoLocationInfoDocumentVo>
): DomainModel

data class KakaoLocationInfoDocumentVo(
    val placeName: String,
    val placePositionX: String,
    val placePositionY: String,
    val addressName: String,
    val roadAddressName: String,
    val documentTotalCount: Int
)