package com.plub.presentation.ui.sign.hobbies

import com.plub.domain.model.vo.common.SelectedHobbyVo
import com.plub.domain.model.vo.signUp.hobbies.SignUpHobbiesVo
import com.plub.presentation.ui.Event

sealed class HobbiesEvent : Event {
    data class MoveToNext(val vo: SignUpHobbiesVo) : HobbiesEvent()
    data class NotifySubHobby(val vo: SelectedHobbyVo) : HobbiesEvent()
    object NotifyAllHobby : HobbiesEvent()
}