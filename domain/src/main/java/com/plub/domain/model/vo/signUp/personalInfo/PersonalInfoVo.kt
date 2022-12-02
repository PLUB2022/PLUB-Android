package com.plub.domain.model.vo.signUp.personalInfo

import com.plub.domain.model.enums.Gender
import com.plub.domain.model.vo.common.Birth
import com.plub.domain.model.vo.signUp.SignUpPageVo

data class PersonalInfoVo(
    val gender: Gender? = null,
    val year:Int = 0,
    val month:Int = 0,
    val day:Int = 0,
) : SignUpPageVo