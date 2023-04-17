package com.plub.domain.model.vo.signUp.hobbies

import com.plub.domain.model.vo.common.SelectedHobbyVo
import com.plub.domain.model.vo.signUp.SignUpPageVo
import java.io.Serializable

data class SignUpHobbiesVo(
    val hobbies:List<SelectedHobbyVo> = emptyList()
) : SignUpPageVo, Serializable