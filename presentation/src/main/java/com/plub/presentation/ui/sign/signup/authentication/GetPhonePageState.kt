package com.plub.presentation.ui.sign.signup.authentication

import com.plub.presentation.ui.PageState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

data class GetPhonePageState(
    val isNextButtonEnable:StateFlow<Boolean>,
    val isSendButtonEnable : StateFlow<Boolean>,
    var phoneNumber : MutableStateFlow<String>,
    val isPhoneNumberEmpty : StateFlow<Boolean>,
    val isCertificationNumberEmpty : StateFlow<Boolean>,
    var certificationNumber : MutableStateFlow<String>,
    val isVisible : StateFlow<Boolean>
): PageState