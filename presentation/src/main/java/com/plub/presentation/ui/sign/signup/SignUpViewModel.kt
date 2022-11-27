package com.plub.presentation.ui.sign.signup

import com.plub.domain.model.enums.SignUpPageType
import com.plub.domain.model.state.SignUpPageState
import com.plub.domain.model.vo.signUp.SignUpPageVo
import com.plub.domain.model.vo.signUp.personalInfo.PersonalInfoVo
import com.plub.domain.model.vo.signUp.terms.TermsPageVo
import com.plub.presentation.base.BaseViewModel
import com.plub.presentation.util.ResourceProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    val resourceProvider: ResourceProvider,
) : BaseViewModel<SignUpPageState>(SignUpPageState()) {

    private val maxSize = SignUpPageType.values().size - 1

    private val signUpPageMap = hashMapOf(
        SignUpPageType.TERMS to TermsPageVo(),
        SignUpPageType.PERSONAL_INFO to PersonalInfoVo(),
    )

    fun onMoveToNextPage(pageType: SignUpPageType, pageVo: SignUpPageVo) {
        signUpPageMap[pageType] = pageVo
        if(isLastPage(pageType)) goToWelcome() else moveToNextPage(pageType)
    }

    private fun goToWelcome() {

    }

    private fun isLastPage(pageType: SignUpPageType):Boolean = pageType.idx == maxSize

    private fun moveToNextPage(pageType: SignUpPageType) {
        updateUiState { uiState ->
            uiState.copy(currentPage = pageType.idx + 1)
        }
    }
}