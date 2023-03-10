package com.plub.domain.model.vo.myPage

import com.plub.domain.model.DomainModel
import com.plub.domain.model.enums.MyPageGatheringStateType

data class MyPageGatheringVo(
    val isExpand : Boolean = false,
    val gatheringList : List<MyPageGatheringDetailVo> = emptyList(),
    val gatheringType : MyPageGatheringStateType = MyPageGatheringStateType.ACTIVE
) : DomainModel
