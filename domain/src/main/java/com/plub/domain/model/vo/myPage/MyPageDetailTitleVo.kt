package com.plub.domain.model.vo.myPage

import com.plub.domain.model.DomainModel

data class MyPageDetailTitleVo(
    val title : String ="",
    val date : List<String> = emptyList(),
    val time : String = "",
    val position : String = ""
) : DomainModel
