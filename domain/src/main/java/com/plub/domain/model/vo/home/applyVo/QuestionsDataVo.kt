package com.plub.domain.model.vo.home.applyVo

import com.plub.domain.model.DomainModel
import com.plub.domain.model.enums.ApplyRecruitQuestionViewType

data class QuestionsDataVo(
    val id : Int = 0,
    val question : String = "",
    val viewType : ApplyRecruitQuestionViewType = ApplyRecruitQuestionViewType.QUESTION,
) : DomainModel