package com.plub.domain.model.vo.signUp.profile

import com.plub.domain.model.vo.signUp.SignUpPageVo
import java.io.File

data class ProfileComposeVo(
    val profileFile: File? = null,
    var nickname:String = ""
):SignUpPageVo