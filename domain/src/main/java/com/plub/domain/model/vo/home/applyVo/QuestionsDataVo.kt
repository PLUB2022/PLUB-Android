package com.plub.domain.model.vo.home.applyVo

import com.plub.domain.base.DomainModel

data class QuestionsDataVo(
    val id : Int,
    val question : String
) : DomainModel()