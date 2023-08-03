package com.plub.presentation.ui.sign.signup.authentication

import androidx.lifecycle.viewModelScope
import com.plub.presentation.base.BaseTestViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GetPhoneViewModel @Inject constructor(
) : BaseTestViewModel<GetPhonePageState>() {

    companion object{
        const val EMPTY_TEXT = ""
        const val FIRST_DASH = 4
        const val SECOND_DASH = 9
        const val CORRECT_NUMBER = 13
    }

    private val isNextButtonEnableStateFlow : MutableStateFlow<Boolean> = MutableStateFlow(false)
    private var phoneNumberStateFlow : MutableStateFlow<String> = MutableStateFlow("")
    private val isEmptyStateFlow : MutableStateFlow<Boolean> = MutableStateFlow(true)

    override val uiState: GetPhonePageState = GetPhonePageState(
        isNextButtonEnable = isNextButtonEnableStateFlow.asStateFlow(),
        phoneNumber = phoneNumberStateFlow,
        isEmpty = isEmptyStateFlow
    )

    fun onTextChangedAfter(){
        when(phoneNumberStateFlow.value.length){
            FIRST_DASH -> addDash(FIRST_DASH - 1)
            SECOND_DASH -> addDash(SECOND_DASH - 1)
        }
        emitEventFlow(GetPhoneEvent.MoveToEnd)
        updateIsEmptyEditText()
        updateButtonState()
    }

    private fun addDash(replaceNum : Int){
        var phoneNumber = ""
        viewModelScope.launch {
            phoneNumber = phoneNumberStateFlow.value.replaceRange(replaceNum,replaceNum,"-")
            phoneNumberStateFlow.update { phoneNumber }
        }
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
}