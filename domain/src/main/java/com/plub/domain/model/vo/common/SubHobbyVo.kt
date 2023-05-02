package com.plub.domain.model.vo.common

import java.io.Serializable

data class SubHobbyVo(
    val id:Int = -1,
    val parentHobbyId:Int = -1,
    val name:String = ""
): Serializable