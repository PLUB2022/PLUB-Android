package com.plub.presentation.ui.main.home.profile.setting

import android.text.SpannableString
import com.plub.presentation.ui.PageState

data class MyPageSettingState(
    val profileImage: String = "",
    var nickname: String ="",
    var introduce: String = "",
    val originNickname : String = "",
    val originIntroduce : String = "",
    val introduceCount : SpannableString = SpannableString(""),
    val nicknameDescription:String = "",
    val nicknameIsActive:Boolean? = null,
    val nicknameIsChanged :Boolean = false
) : PageState {
    val isSaveButtonEnable = introduce.isNotEmpty() && nickname.isNotEmpty() && (introduce != originIntroduce)
            && (nicknameIsActive == true)
}