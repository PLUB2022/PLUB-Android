package com.plub.presentation.ui.sign.signup

import androidx.lifecycle.viewModelScope
import com.plub.domain.model.enums.SignUpPageType
import com.plub.domain.model.state.SignUpPageState
import com.plub.domain.model.vo.signUp.SignUpPageVo
import com.plub.domain.model.vo.signUp.personalInfo.PersonalInfoVo
import com.plub.domain.model.vo.signUp.profile.ProfileInfoVo
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

    private val signUpPageMap:MutableMap<SignUpPageType,SignUpPageVo> = mutableMapOf()

    private val _navigationPop = MutableSharedFlow<Unit>(0, 1, BufferOverflow.DROP_OLDEST)
    val navigationPop: SharedFlow<Unit> = _navigationPop.asSharedFlow()

    private val _initPage = MutableSharedFlow<Pair<SignUpPageType,SignUpPageVo?>>(0, 1, BufferOverflow.DROP_OLDEST)
    val initPage: SharedFlow<Pair<SignUpPageType,SignUpPageVo?>> = _initPage.asSharedFlow()

    private val _testInitPage = MutableStateFlow(TermsPageVo())
    val testInitPage: StateFlow<TermsPageVo> = _testInitPage.asStateFlow()

    fun onBackPressed(currentPage: Int) {
        val previousPage = currentPage - 1
        if (isFirstPage(currentPage)) goToNavUp() else moveToPage(previousPage)
    }

    fun onMoveToNextPage(pageType: SignUpPageType, pageVo: SignUpPageVo) {
        signUpPageMap[pageType] = pageVo
        val currentPage = pageType.idx
        val nextPage = currentPage + 1
        if (isLastPage(currentPage)) goToWelcome() else {
            moveToPage(nextPage)
            initPage(SignUpPageType.valueOf(nextPage))
        }
    }

    fun testInit() {
        viewModelScope.launch {
            _testInitPage.emit(TermsPageVo(true,true,true,true,true,))
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

    private fun initPage(pageType: SignUpPageType) {
        viewModelScope.launch {
            _initPage.emit(Pair(pageType,signUpPageMap[pageType]))
        }
    }
}