package com.plub.domain.model.vo.myPage

import com.plub.domain.model.DomainModel
import com.plub.domain.model.enums.MyPageGatheringType

data class MyPageGatheringDetailVo(
    val image : String = "",
    val title : String = "",
    val goal : String = "",
    val gatheringType : MyPageGatheringType = MyPageGatheringType.RECRUITING
) : DomainModel