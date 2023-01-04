package com.plub.presentation.state

import com.plub.domain.model.vo.common.HobbyVo
import com.plub.domain.model.vo.signUp.hobbies.SignUpHobbiesVo

data class HobbiesPageState(
    val isNextButtonEnable:Boolean = false,
    val hobbiesVo:List<HobbyVo> = emptyList(),
    val descriptionText:String = "",
    val hobbiesSelectedVo: SignUpHobbiesVo = SignUpHobbiesVo()
): PageState