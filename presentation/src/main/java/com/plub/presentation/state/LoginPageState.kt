package com.plub.presentation.state

import android.text.SpannableString

data class LoginPageState(
    val authCode:String = "",
    val termsText: SpannableString = SpannableString("")
): PageState