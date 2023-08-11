package com.plub.presentation.ui.sign.signup.authentication

import androidx.lifecycle.viewModelScope
import com.plub.domain.model.vo.account.SmsCertificationRequestVo
import com.plub.domain.usecase.PostSendSmsUseCase
import com.plub.domain.usecase.PostSmsCertificationUseCase
import com.plub.presentation.base.BaseTestViewModel
import com.plub.presentation.ui.sign.signup.personalInfo.PersonalInfoEvent
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
        const val CORRECT_CERTIFICATION_COUNT = 6
    }

    private val isNextButtonEnableStateFlow : MutableStateFlow<Boolean> = MutableStateFlow(false)
    private val isSendButtonEnableStateFlow : MutableStateFlow<Boolean> = MutableStateFlow(false)
    private var phoneNumberStateFlow : MutableStateFlow<String> = MutableStateFlow("")
    private var certificationNumberStateFlow : MutableStateFlow<String> = MutableStateFlow("")
    private val isPhoneNumberEmptyStateFlow : MutableStateFlow<Boolean> = MutableStateFlow(true)
    private val isCertificationNumberEmptyStateFlow : MutableStateFlow<Boolean> = MutableStateFlow(true)
    private var isVisibleStateFlow : MutableStateFlow<Boolean> = MutableStateFlow(false)

    override val uiState: GetPhonePageState = GetPhonePageState(
        isNextButtonEnable = isNextButtonEnableStateFlow.asStateFlow(),
        isSendButtonEnable = isSendButtonEnableStateFlow.asStateFlow(),
        phoneNumber = phoneNumberStateFlow,
        certificationNumber = certificationNumberStateFlow,
        isCertificationNumberEmpty = isCertificationNumberEmptyStateFlow.asStateFlow(),
        isPhoneNumberEmpty = isPhoneNumberEmptyStateFlow.asStateFlow(),
        isVisible = isVisibleStateFlow.asStateFlow()
    )

    fun onPhoneNumberTextChangedAfter(){
        updateIsEmptyEditText()
        updateButtonState()
    }

    fun onCertificationTextChangedAfter(){
        viewModelScope.launch {
            isCertificationNumberEmptyStateFlow.update { uiState.certificationNumber.value.isEmpty() }
        }
        confirmCertification()
    }

    private fun confirmCertification(){
        if(uiState.certificationNumber.value.length == CORRECT_CERTIFICATION_COUNT){
            val request = SmsCertificationRequestVo(
                phone = getSplitDashPhone(),
                certificationNum = uiState.certificationNumber.value
            )

            viewModelScope.launch {
                postSmsCertificationUseCase(request).collect{
                    inspectUiState(it, {handleSuccessCertification()})
                }
            }
        }
        else{
            updateEnAbleNextButton()
            emitEventFlow(GetPhoneEvent.EditTextNorMalColor)
        }
    }

    private fun handleSuccessCertification(){
        updateAbleNextButton()
        emitEventFlow(GetPhoneEvent.CertificationSuccess)
    }

    private fun updateAbleNextButton(){
        viewModelScope.launch {
            isNextButtonEnableStateFlow.update { true }
        }
    }

    private fun updateEnAbleNextButton(){
        viewModelScope.launch {
            isNextButtonEnableStateFlow.update { false }
        }
    }

    private fun updateIsEmptyEditText(){
        viewModelScope.launch {
            isPhoneNumberEmptyStateFlow.update { uiState.phoneNumber.value.isEmpty() }
        }
    }

    private fun updateButtonState(){
        viewModelScope.launch {
            isSendButtonEnableStateFlow.update { uiState.phoneNumber.value.length == CORRECT_NUMBER}
        }
    }

    fun onClickClearButton(){
        viewModelScope.launch {
            phoneNumberStateFlow.update { EMPTY_TEXT }
        }
    }

    fun onClickSendButton(){
        emitEventFlow(GetPhoneEvent.ShowBottomSheet)
    }

    fun onClickSendAgainButton(){
        val phoneNum = getSplitDashPhone()
        viewModelScope.launch {
            postSendSmsUseCase(phoneNum).collect{
                inspectUiState(it, { emitEventFlow(GetPhoneEvent.TimerStart)} )
            }
            isVisibleStateFlow.update { true }
        }
    }

    fun onClickNextButton() {
        emitEventFlow(GetPhoneEvent.MoveToNext)
    }

    private fun getSplitDashPhone() : String {
        val phoneNumArray = phoneNumberStateFlow.value.split("-")
        return phoneNumArray[0] + phoneNumArray[1] + phoneNumArray[2]
    }
}