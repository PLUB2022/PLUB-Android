package com.plub.presentation.ui.sign.signup.moreInfo

import com.plub.domain.model.vo.signUp.moreInfo.MoreInfoVo
import com.plub.presentation.ui.Event

sealed class MoreInfoEvent : Event {
    data class MoveToNext(val vo: MoreInfoVo) : MoreInfoEvent()
}