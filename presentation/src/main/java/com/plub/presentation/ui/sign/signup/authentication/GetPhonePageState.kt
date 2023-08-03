package com.plub.presentation.ui.sign.signup.authentication

import com.plub.presentation.ui.PageState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

data class GetPhonePageState(
    val isNextButtonEnable:StateFlow<Boolean>,
    var phoneNumber : MutableStateFlow<String>,
): PageState