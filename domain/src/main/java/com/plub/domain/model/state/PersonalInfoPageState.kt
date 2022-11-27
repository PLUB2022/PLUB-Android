package com.plub.domain.model.state

import com.plub.domain.model.vo.signUp.personalInfo.PersonalInfoVo

data class PersonalInfoPageState(
    val isNextButtonEnable:Boolean = false,
    val personalInfoVo: PersonalInfoVo = PersonalInfoVo()
): PageState