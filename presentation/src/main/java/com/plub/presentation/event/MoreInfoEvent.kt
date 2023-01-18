package com.plub.presentation.event

import com.plub.domain.model.vo.signUp.moreInfo.MoreInfoVo

sealed class MoreInfoEvent : Event {
    data class MoveToNext(val vo: MoreInfoVo) : MoreInfoEvent()
}