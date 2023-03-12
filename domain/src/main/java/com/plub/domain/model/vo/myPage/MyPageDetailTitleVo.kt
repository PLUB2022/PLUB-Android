package com.plub.domain.model.vo.myPage

import com.plub.domain.model.DomainModel
import com.plub.domain.model.enums.MyPageGatheringMyType

data class MyPageDetailTitleVo(
    val title : String ="",
    val date : List<String> = emptyList(),
    val time : String = "",
    val position : String = "",
    val viewType : MyPageGatheringMyType = MyPageGatheringMyType.END
) : DomainModel
