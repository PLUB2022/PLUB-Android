package com.plub.domain.model.vo.plub

import com.plub.domain.model.DomainModel

data class PlubingMainVo(
    val plubingId: Int = -1,
    val name: String = "",
    val goal: String = "",
    val mainImage: String = "",
    val days: List<String> = emptyList(),
    val time: String = "",
    val address: String = "",
    val roadAddress: String = "",
    val placeName: String = "",
    val memberInfoList: List<PlubingMemberInfoVo> = emptyList()
) : DomainModel