package com.plub.domain.model.vo.signUp.profile

import com.plub.domain.model.vo.signUp.SignUpPageVo
import java.io.File

data class ProfileInfoVo(
    val profileFile:File? = null,
    val nickname:String = ""
):SignUpPageVo