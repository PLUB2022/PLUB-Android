package com.plub.presentation.state

import com.plub.domain.model.vo.signUp.profile.ProfileComposeVo

data class ProfileComposePageState(
    val isNextButtonEnable:Boolean = false,
    val nicknameDescription:String = "",
    val nicknameIsActive:Boolean? = null,
    val profileComposeVo: ProfileComposeVo = ProfileComposeVo()
): PageState