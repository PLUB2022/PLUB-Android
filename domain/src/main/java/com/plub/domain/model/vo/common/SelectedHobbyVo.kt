package com.plub.domain.model.vo.common

import java.io.Serializable

data class SelectedHobbyVo(
    val parentId:Int,
    val subId:Int,
    val name: String
): Serializable