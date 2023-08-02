package com.plub.presentation.ui.sign.signup.personalInfo

import com.plub.domain.model.vo.signUp.personalInfo.PersonalInfoVo
import com.plub.presentation.ui.PageState

data class PersonalInfoPageState(
    val isNextButtonEnable:Boolean = false,
    val birthString:String = "",
    val birthIsActive:Boolean = false,
    val personalInfoVo: PersonalInfoVo = PersonalInfoVo()
): PageState