package com.plub.presentation.ui.sign.signup

import androidx.lifecycle.viewModelScope
import com.plub.domain.model.enums.SignUpPageType
import com.plub.domain.model.state.SignUpPageState
import com.plub.domain.model.vo.signUp.SignUpPageVo
import com.plub.domain.model.vo.signUp.personalInfo.PersonalInfoVo
import com.plub.domain.model.vo.signUp.terms.TermsPageVo
import com.plub.presentation.base.BaseViewModel
import com.plub.presentation.util.ResourceProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    val resourceProvider: ResourceProvider,
) : BaseViewModel<SignUpPageState>(SignUpPageState()) {

    private val maxSize = SignUpPageType.values().size - 1

    private val _navigationPop = MutableSharedFlow<Unit>(0, 1, BufferOverflow.DROP_OLDEST)
    val navigationPop: SharedFlow<Unit> = _navigationPop.asSharedFlow()

    fun onBackPressed(currentPage: Int) {
        val previousPage = currentPage - 1
        if (isFirstPage(currentPage)) goToNavUp() else moveToPage(previousPage)
    }

    fun onMoveToNextPage(pageType: SignUpPageType, pageVo: SignUpPageVo) {
        updatePageVo(pageVo)
        val currentPage = pageType.idx
        val nextPage = currentPage + 1
        if (isLastPage(currentPage)) goToWelcome() else {
            moveToPage(nextPage)
        }
    }

    private fun updatePageVo(pageVo: SignUpPageVo) {
        when(pageVo) {
            is TermsPageVo -> {
                updateUiState {
                    it.copy(termsPageVo = pageVo)
                }
            }
            is PersonalInfoVo -> {
                updateUiState {
                    it.copy(personalInfoVo = pageVo)
                }
            }
        }
    }

    private fun goToWelcome() {

    }

    private fun goToNavUp() {
        viewModelScope.launch {
            _navigationPop.emit(Unit)
        }
    }

    private fun isFirstPage(currentPage: Int) = currentPage == 0

    private fun isLastPage(currentPage: Int): Boolean = currentPage == maxSize

    private fun moveToPage(page: Int) {
        updateUiState { uiState ->
            uiState.copy(currentPage = page)
        }
    }
}