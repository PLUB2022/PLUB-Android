package com.plub.presentation.ui.sign.personalInfo

import androidx.lifecycle.viewModelScope
import com.plub.domain.model.enums.Gender
import com.plub.domain.model.enums.TermsType
import com.plub.domain.model.state.PageState
import com.plub.domain.model.state.PersonalInfoPageState
import com.plub.domain.model.vo.signUp.personalInfo.PersonalInfoVo
import com.plub.domain.model.vo.signUp.terms.TermsAgreementItemVo
import com.plub.domain.model.vo.signUp.terms.TermsPageVo
import com.plub.presentation.base.BaseViewModel
import com.plub.presentation.util.ResourceProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PersonalInfoViewModel @Inject constructor(
    val resourceProvider: ResourceProvider,
) : BaseViewModel<PersonalInfoPageState>(PersonalInfoPageState()) {

    private val _moveToNextPage = MutableSharedFlow<PersonalInfoVo>(replay = 0, extraBufferCapacity = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST)
    val moveToNextPage: SharedFlow<PersonalInfoVo> = _moveToNextPage.asSharedFlow()

    init {
        viewModelScope.launch {
            uiState.collect {
                verifyNextButtonEnable()
            }
        }
    }

    fun onClickGender(gender: Gender) {
        updateUiState { ui ->
            ui.copy(
                personalInfoVo = ui.personalInfoVo.copy(
                    gender = gender
                )
            )
        }
    }

    fun onClickNextButton() {
        viewModelScope.launch {
            _moveToNextPage.emit(uiState.value.personalInfoVo)
        }
    }

    private fun verifyNextButtonEnable() {
        updateUiState {
            it.copy(
                isNextButtonEnable = isNextButtonEnable(it.personalInfoVo)
            )
        }
    }

    private fun isNextButtonEnable(personalInfoVo: PersonalInfoVo):Boolean {
        return personalInfoVo.run {
            gender != null && birth.year != 0 && birth.month != 0 && birth.day != 0
        }
    }
}