package com.plub.domain.model.vo.modifyGathering

import com.plub.domain.model.DomainModel

data class ModifyQuestionRequestVo(
    val plubbingId: Int,
    val questions: List<String>
): DomainModel