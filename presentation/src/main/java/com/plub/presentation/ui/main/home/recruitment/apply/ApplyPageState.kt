package com.plub.presentation.ui.main.home.recruitment.apply

import com.plub.domain.model.enums.ApplyModifyApplicationType
import com.plub.domain.model.vo.home.applyVo.QuestionsDataVo
import com.plub.presentation.ui.PageState

data class ApplyPageState (
    val isApplyButtonEnable:Boolean = false,
    val questions : List<QuestionsDataVo> = emptyList(),
    val pageType : ApplyModifyApplicationType = ApplyModifyApplicationType.APPLY
) : PageState
