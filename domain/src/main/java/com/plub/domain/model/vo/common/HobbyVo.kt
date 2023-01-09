package com.plub.domain.model.vo.common

import com.plub.domain.model.enums.HobbyViewType

data class HobbyVo(
    val viewType:HobbyViewType = HobbyViewType.HOBBY,
    val id:Int = -1,
    val name:String = "",
    val icon:String = "",
    val isExpand:Boolean = false,
    val subHobbies:List<SubHobbyVo> = emptyList()
)