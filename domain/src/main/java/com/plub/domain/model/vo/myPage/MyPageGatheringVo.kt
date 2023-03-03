package com.plub.domain.model.vo.myPage

data class MyPageGatheringVo(
    val gatheringList : List<String> = emptyList(),
    val gatheringType : Int = 0
)
