package com.plub.domain.model.vo.plub

import com.plub.domain.base.DomainModel
import com.plub.domain.model.enums.PlubCardType

data class PlubCardVo(
    val id : Int = 1,
    val viewType: PlubCardType = PlubCardType.GRID,
    val photo: String = "",
    val name : String = "",
    val title : String = "",
    val time : String = "",
    val days : List<String> = emptyList(),
    val place:String = "",
    val remainMemberNumber: Int = -1,
    val isBookmarked : Boolean = false
): DomainModel