package com.plub.domain.model.vo.myPage

import com.plub.domain.model.DomainModel
import com.plub.domain.model.enums.MyPageGatheringMyType

data class MyPageGatheringDetailVo(
    val image : String = "",
    val title : String = "",
    val goal : String = "",
    val gatheringType : MyPageGatheringMyType = MyPageGatheringMyType.GUEST
) : DomainModel