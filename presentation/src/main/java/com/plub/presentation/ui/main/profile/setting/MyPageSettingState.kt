package com.plub.presentation.ui.main.profile.setting

import android.text.SpannableString
import com.plub.presentation.ui.PageState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

data class MyPageSettingState(
    val profileImage: StateFlow<String?>,
    val originProfile : StateFlow<String>,
    var nickname: MutableStateFlow<String>,
    var introduce: MutableStateFlow<String>,
    val originNickname : StateFlow<String>,
    val originIntroduce : StateFlow<String>,
    val introduceCount : StateFlow<SpannableString>,
    val nicknameDescription: StateFlow<String>,
    val nicknameIsActive:StateFlow<Boolean?>,
    val nicknameIsChanged :StateFlow<Boolean>,
    val isSaveButtonEnable : StateFlow<Boolean>
) : PageState