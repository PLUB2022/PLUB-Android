package com.plub.presentation.ui.sign.signup.authentication

import com.plub.domain.model.vo.signUp.authentication.PhoneNumberVo
import com.plub.presentation.ui.Event

sealed class GetPhoneEvent : Event {
    object ShowBottomSheet : GetPhoneEvent()
    object CertificationSuccess : GetPhoneEvent()
    object EditTextNorMalColor : GetPhoneEvent()
    data class MoveToNext(val phone : PhoneNumberVo) : GetPhoneEvent()
    object TimerStart : GetPhoneEvent()
    data class ShowErrorToast(val msg : Int) : GetPhoneEvent()
}