package com.plub.domain.model.vo.signUp.personalInfo

import com.plub.domain.model.enums.Gender
import com.plub.domain.model.vo.common.Birth
import com.plub.domain.model.vo.signUp.SignUpPageVo
import java.util.Calendar

data class PersonalInfoVo(
    val gender: Gender? = null,
    val calendar: Calendar? = null
) : SignUpPageVo