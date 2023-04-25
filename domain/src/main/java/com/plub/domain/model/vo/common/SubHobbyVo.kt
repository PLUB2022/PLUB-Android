package com.plub.domain.model.vo.common

data class SubHobbyVo(
    val id:Int = -1,
    val parentHobbyId:Int = -1,
    val name:String = ""
): java.io.Serializable