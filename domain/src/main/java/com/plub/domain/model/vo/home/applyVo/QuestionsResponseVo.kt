package com.plub.domain.model.vo.home.applyVo

import com.plub.domain.model.DomainModel

data class QuestionsResponseVo(
    val questions : List<QuestionsDataVo> = emptyList()
) : DomainModel