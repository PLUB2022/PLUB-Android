package com.plub.presentation.event

import com.plub.domain.model.enums.TermsType
import com.plub.domain.model.vo.common.SelectedHobbyVo
import com.plub.domain.model.vo.signUp.hobbies.SignUpHobbiesVo

sealed class MainEvent : Event {
    data class ShowBottomNavigationBadge(val index:Int) : MainEvent()
    data class BottomNavigationVisibility(val isVisible:Boolean) : MainEvent()
}
