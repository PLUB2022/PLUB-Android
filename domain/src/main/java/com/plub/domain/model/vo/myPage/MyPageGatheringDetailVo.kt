package com.plub.domain.model.vo.myPage

import com.plub.domain.model.DomainModel

data class MyPageGatheringDetailVo(
    val image : String = "",
    val title : String = "",
    val goal : String = "",
    val gatheringType : String = ""
) : DomainModel