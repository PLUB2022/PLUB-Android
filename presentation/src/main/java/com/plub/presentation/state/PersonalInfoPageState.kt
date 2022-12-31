package com.plub.presentation.state

import com.plub.domain.model.vo.signUp.personalInfo.PersonalInfoVo

data class PersonalInfoPageState(
    val isNextButtonEnable:Boolean = false,
    val birthString:String = "",
    val birthIsActive:Boolean = false,
    val personalInfoVo: PersonalInfoVo = PersonalInfoVo()
): PageState