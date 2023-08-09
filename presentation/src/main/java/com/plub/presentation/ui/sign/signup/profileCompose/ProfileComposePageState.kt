package com.plub.presentation.ui.sign.signup.profileCompose

import android.text.SpannableString
import com.plub.domain.model.vo.signUp.moreInfo.MoreInfoVo
import com.plub.domain.model.vo.signUp.profile.ProfileComposeVo
import com.plub.presentation.ui.PageState

data class ProfileComposePageState(
    val isNextButtonEnable:Boolean = false,
    val nicknameDescription:String = "",
    val nicknameIsActive:Boolean? = null,
    val profileComposeVo: ProfileComposeVo = ProfileComposeVo(),
    val introduceCount: SpannableString = SpannableString(""),
): PageState