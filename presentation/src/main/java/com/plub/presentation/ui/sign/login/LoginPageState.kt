package com.plub.presentation.ui.sign.login

import android.text.SpannableString
import com.plub.presentation.ui.PageState

data class LoginPageState(
    val authCode:String = "",
    val termsText: SpannableString = SpannableString("")
): PageState