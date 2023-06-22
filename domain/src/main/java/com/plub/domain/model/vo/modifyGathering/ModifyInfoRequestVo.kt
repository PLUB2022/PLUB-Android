package com.plub.domain.model.vo.modifyGathering

import com.plub.domain.model.DomainModel

data class ModifyInfoRequestVo(
    val plubbingId: Int,
    val days:List<String>,
    val onOff: String,
    val maxAccountNum: Int,
    val address: String,
    val roadAddress: String,
    val placeName: String,
    val placePositionX: Float = 0f,
    val placePositionY: Float = 0f,
    val time: String,
): DomainModel