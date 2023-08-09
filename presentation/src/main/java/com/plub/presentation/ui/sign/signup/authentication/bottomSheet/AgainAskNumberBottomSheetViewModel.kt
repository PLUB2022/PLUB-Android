package com.plub.presentation.ui.sign.signup.authentication.bottomSheet

import com.plub.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AgainAskNumberBottomSheetViewModel @Inject constructor(
) : BaseViewModel<AgainAskNumberBottomSheetPageState>(
    AgainAskNumberBottomSheetPageState()
) {

    fun initView(phoneNumber : String){
        updateUiState { uiState ->
            uiState.copy(phoneNumber = phoneNumber)
        }
    }

    fun onClickSendButton(){
        emitEventFlow(AgainAskNumberBottomSheetEvent.ClickSendButton)
    }

    fun onClickCloseButton(){
        emitEventFlow(AgainAskNumberBottomSheetEvent.ClickCloseButton)
    }

    fun onClickCheckBox(){
        updateUiState { uiState ->
            uiState.copy(isChecked = !uiState.isChecked)
        }
    }
}