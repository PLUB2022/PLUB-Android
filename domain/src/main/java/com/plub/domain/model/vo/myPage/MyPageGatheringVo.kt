package com.plub.domain.model.vo.myPage

import com.plub.domain.model.DomainModel
import com.plub.domain.model.enums.MyPageGatheringType

data class MyPageGatheringVo(
    val isExpand : Boolean = false,
    val gatheringList : List<MyPageGatheringDetailVo> = emptyList(),
    val gatheringType : MyPageGatheringType = MyPageGatheringType.RECRUITING
) : DomainModel
