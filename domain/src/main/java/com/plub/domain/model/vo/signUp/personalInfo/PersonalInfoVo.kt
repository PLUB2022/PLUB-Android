package com.plub.domain.model.vo.signUp.personalInfo

import com.plub.domain.model.enums.Gender
import com.plub.domain.model.vo.signUp.SignUpPageVo

data class PersonalInfoVo(
    val gender: Gender = Gender.MAN
) : SignUpPageVo