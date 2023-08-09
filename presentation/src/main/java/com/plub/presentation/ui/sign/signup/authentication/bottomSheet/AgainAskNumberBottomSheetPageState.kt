package com.plub.presentation.ui.sign.signup.authentication.bottomSheet

import com.plub.presentation.ui.PageState

data class AgainAskNumberBottomSheetPageState(
    var isChecked: Boolean = false,
    val phoneNumber : String = ""
): PageState