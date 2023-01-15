package com.plub.presentation.event

import com.plub.domain.model.vo.common.SelectedHobbyVo
import com.plub.domain.model.vo.signUp.hobbies.SignUpHobbiesVo

sealed class HobbiesEvent : Event {
    data class MoveToNext(val vo: SignUpHobbiesVo) : HobbiesEvent()
    data class NotifySubHobby(val vo: SelectedHobbyVo) : HobbiesEvent()
    object NotifyAllHobby : HobbiesEvent()
}