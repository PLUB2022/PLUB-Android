package com.plub.presentation.ui.sign.signup.terms

import com.plub.domain.model.vo.signUp.terms.TermsPageVo
import com.plub.presentation.ui.Event

sealed class TermsEvent : Event {
    data class MoveToNext(val vo: TermsPageVo) : TermsEvent()
}