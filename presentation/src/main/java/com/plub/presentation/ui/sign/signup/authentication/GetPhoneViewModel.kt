package com.plub.presentation.ui.sign.signup.authentication

import androidx.lifecycle.viewModelScope
import com.plub.domain.model.vo.account.SmsCertificationRequestVo
import com.plub.domain.usecase.PostSendSmsUseCase
import com.plub.domain.usecase.PostSmsCertificationUseCase
import com.plub.presentation.base.BaseTestViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GetPhoneViewModel @Inject constructor(
    private val postSendSmsUseCase: PostSendSmsUseCase,
    private val postSmsCertificationUseCase: PostSmsCertificationUseCase
) : BaseTestViewModel<GetPhonePageState>() {

    companion object{
        const val EMPTY_TEXT = ""
        const val CORRECT_NUMBER = 13
    }

    private val isNextButtonEnableStateFlow : MutableStateFlow<Boolean> = MutableStateFlow(false)
    private var phoneNumberStateFlow : MutableStateFlow<String> = MutableStateFlow("")
    private val isEmptyStateFlow : MutableStateFlow<Boolean> = MutableStateFlow(true)
    private var testStateFlow : MutableStateFlow<String> = MutableStateFlow("")

    override val uiState: GetPhonePageState = GetPhonePageState(
        isNextButtonEnable = isNextButtonEnableStateFlow.asStateFlow(),
        phoneNumber = phoneNumberStateFlow,
        isEmpty = isEmptyStateFlow,
        test = testStateFlow
    )

    fun onTextChangedAfter(){
        updateIsEmptyEditText()
        updateButtonState()
    }

    private fun updateIsEmptyEditText(){
        viewModelScope.launch {
            isEmptyStateFlow.update { uiState.phoneNumber.value.isEmpty() }
        }
    }

    private fun updateButtonState(){
        viewModelScope.launch {
            isNextButtonEnableStateFlow.update { uiState.phoneNumber.value.length == CORRECT_NUMBER}
        }
    }

    fun onClickClearButton(){
        viewModelScope.launch {
            phoneNumberStateFlow.update { EMPTY_TEXT }
        }
    }

    fun onClickSendButton(){
        val phoneNum = getSplitDashPhone()
        viewModelScope.launch {
            postSendSmsUseCase(phoneNum).collect()
        }
    }

    fun test(){
        val request = SmsCertificationRequestVo(
            phone = getSplitDashPhone(),
            certificationNum = testStateFlow.value.toInt()
        )

        viewModelScope.launch {
            postSmsCertificationUseCase(request).collect()
        }
    }

    private fun getSplitDashPhone() : String {
        val phoneNumArray = phoneNumberStateFlow.value.split("-")
        return phoneNumArray[0] + phoneNumArray[1] + phoneNumArray[2]
    }
}