package com.plub.presentation.state

import com.plub.domain.model.vo.home.applyVo.QuestionsDataVo
import com.plub.presentation.ui.PageState

data class ApplyPageState (
    val isApplyButtonEnable:Boolean = false,
    val questions : List<QuestionsDataVo> = emptyList()
) : PageState
