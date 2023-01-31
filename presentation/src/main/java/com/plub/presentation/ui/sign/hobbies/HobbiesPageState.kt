package com.plub.presentation.ui.sign.hobbies

import com.plub.domain.model.vo.common.HobbyVo
import com.plub.domain.model.vo.signUp.hobbies.SignUpHobbiesVo
import com.plub.presentation.ui.PageState

data class HobbiesPageState(
    val isNextButtonEnable:Boolean = false,
    val hobbiesVo:List<HobbyVo> = emptyList(),
    val descriptionText:String = "",
    val hobbiesSelectedVo: SignUpHobbiesVo = SignUpHobbiesVo()
): PageState