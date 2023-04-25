package com.plub.domain.model.vo.plub

import com.plub.domain.model.DomainModel

data class PlubingMemberInfoVo(
    val id:Int = -1,
    val nickname: String = "",
    val profileImage: String? = ""
) : DomainModel