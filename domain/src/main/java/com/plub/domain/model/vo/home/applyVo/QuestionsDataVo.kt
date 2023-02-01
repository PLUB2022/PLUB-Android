package com.plub.domain.model.vo.home.applyVo

import com.plub.domain.model.DomainModel
import com.plub.domain.model.enums.QuestionDataType

data class QuestionsDataVo(
    val id : Int = 0,
    val question : String = "",
    val viewType : QuestionDataType = QuestionDataType.DATA,
) : DomainModel