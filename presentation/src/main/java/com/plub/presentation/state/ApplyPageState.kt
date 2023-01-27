package com.plub.presentation.state

import com.plub.domain.model.vo.home.applyVo.QuestionsResponseVo

data class ApplyPageState (
    val isApplyButtonEnable:Boolean = false,
    val questionsData : QuestionsResponseVo = QuestionsResponseVo(emptyList())
) : PageState
