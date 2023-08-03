package com.plub.presentation.ui.sign.signup.authentication

import com.plub.presentation.base.BaseTestViewModel
import com.plub.presentation.util.PlubLogger
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class GetPhoneViewModel @Inject constructor(
) : BaseTestViewModel<GetPhonePageState>() {

    private val isNextButtonEnableStateFlow : MutableStateFlow<Boolean> = MutableStateFlow(false)
    private var phoneNumberStateFlow : MutableStateFlow<String> = MutableStateFlow("")

    override val uiState: GetPhonePageState = GetPhonePageState(
        isNextButtonEnable = isNextButtonEnableStateFlow.asStateFlow(),
        phoneNumber = phoneNumberStateFlow
    )

    fun onTextChangedAfter(){
        PlubLogger.logD(uiState.phoneNumber.value)
    }
}