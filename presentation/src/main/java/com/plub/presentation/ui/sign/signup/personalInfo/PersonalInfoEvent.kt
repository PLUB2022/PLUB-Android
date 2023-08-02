package com.plub.presentation.ui.sign.signup.personalInfo

import com.plub.domain.model.vo.signUp.personalInfo.PersonalInfoVo
import com.plub.presentation.ui.Event
import java.util.*

sealed class PersonalInfoEvent : Event {
    data class MoveToNext(val vo: PersonalInfoVo) : PersonalInfoEvent()
    data class ShowDatePickerDialog(val calendar: Calendar) : PersonalInfoEvent()
}