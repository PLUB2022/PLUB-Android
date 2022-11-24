package com.plub.presentation.ui.sign.signup

import com.plub.domain.model.state.SignUpPageState
import com.plub.presentation.base.BaseViewModel
import com.plub.presentation.util.ResourceProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    val resourceProvider: ResourceProvider,
) : BaseViewModel<SignUpPageState>(SignUpPageState()) {

    fun onClickNextButton() {

    }

    fun onChangeNextButton(isEnable:Boolean) {
        updateUiState { ui ->
            ui.copy(isNextButtonEnable = isEnable)
        }
    }
}