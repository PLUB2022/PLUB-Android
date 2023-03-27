package com.plub.domain.model.vo.myGathering

import com.plub.domain.model.DomainModel
import com.plub.domain.model.enums.MyGatheringsViewType

data class MyGatheringsResponseVo(
    val plubbings: List<MyGatheringResponseVo> = emptyList()
): DomainModel

data class MyGatheringResponseVo(
    val viewType: MyGatheringsViewType = MyGatheringsViewType.MY_GATHERING_CONTENT,
    val plubbingId: Int = -1,
    val name: String = "",
    val goal: String = "",
    val mainImage: String = "",
    val time: String = "00:00",
    val days: List<String> = emptyList()
): DomainModel
