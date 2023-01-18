package com.plub.presentation.event

import com.plub.domain.model.vo.signUp.personalInfo.PersonalInfoVo
import java.util.*

sealed class PersonalInfoEvent : Event {
    data class MoveToNext(val vo: PersonalInfoVo) : PersonalInfoEvent()
    data class ShowDatePickerDialog(val calendar: Calendar) : PersonalInfoEvent()
}