package com.plub.domain.model.vo.createGathering

import com.plub.domain.base.DomainModel

data class CreateGatheringRequestVo(
    val subCategoryIds: List<Int>,
    val title:String,
    val name: String,
    val goal: String,
    val introduce: String,
    val mainImage: String,
    val time: String,
    val days: List<String>,
    val onOff: String,
    val address: String?,
    val roadAddress: String?,
    val placeName: String?,
    val placePositionX: Float?,
    val placePositionY: Float?,
    val maxAccountNum: Int,
    val questions: List<String>
): DomainModel