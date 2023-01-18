package com.plub.presentation.event

import com.plub.domain.model.vo.signUp.terms.TermsPageVo

sealed class TermsEvent : Event {
    data class MoveToNext(val vo: TermsPageVo) : TermsEvent()
}