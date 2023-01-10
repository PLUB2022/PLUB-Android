package com.plub.presentation.ui.sign.signup

import androidx.lifecycle.viewModelScope
import com.plub.domain.error.SignUpError
import com.plub.domain.model.enums.SignUpPageType
import com.plub.domain.model.enums.UploadFileType
import com.plub.domain.model.vo.jwt_token.PlubJwtResponseVo
import com.plub.domain.model.vo.media.UploadFileRequestVo
import com.plub.domain.model.vo.signUp.SignUpPageVo
import com.plub.domain.model.vo.signUp.hobbies.SignUpHobbiesVo
import com.plub.domain.model.vo.signUp.moreInfo.MoreInfoVo
import com.plub.domain.model.vo.signUp.personalInfo.PersonalInfoVo
import com.plub.domain.model.vo.signUp.profile.ProfileComposeVo
import com.plub.domain.model.vo.signUp.terms.TermsPageVo
import com.plub.domain.usecase.PostSignUpUseCase
import com.plub.domain.usecase.PostUploadFileUseCase
import com.plub.presentation.base.BaseViewModel
import com.plub.presentation.state.SignUpPageState
import com.plub.presentation.util.DataStoreUtil
import com.plub.presentation.util.ResourceProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    val resourceProvider: ResourceProvider,
    val datsStoreUtil: DataStoreUtil,
    val postSignUpUseCase: PostSignUpUseCase,
    val postUploadFileUseCase: PostUploadFileUseCase,
) : BaseViewModel<SignUpPageState>(SignUpPageState()) {

    private var isNetworkCall = false

    private val _navigationPop = MutableSharedFlow<Unit>(0, 1, BufferOverflow.DROP_OLDEST)
    val navigationPop: SharedFlow<Unit> = _navigationPop.asSharedFlow()

    private val _showSignUpErrorDialog = MutableSharedFlow<String>(0, 1, BufferOverflow.DROP_OLDEST)
    val showSignUpErrorDialog: SharedFlow<String> = _showSignUpErrorDialog.asSharedFlow()

    fun onBackPressed(currentPage: Int) {
        val previousPage = currentPage - 1
        if (isFirstPage(currentPage)) goToNavUp() else moveToPage(previousPage)
    }

    fun onMoveToNextPage(pageType: SignUpPageType, pageVo: SignUpPageVo) {
        updatePageVo(pageVo)
        val currentPage = pageType.idx
        val nextPage = currentPage + 1
        if (isLastPage(currentPage)) signUpProcess() else {
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
            is ProfileComposeVo -> {
                updateUiState {
                    it.copy(profileComposeVo = pageVo)
                }
            }
            is MoreInfoVo -> {
                updateUiState {
                    it.copy(moreInfoVo = pageVo)
                }
            }

            is SignUpHobbiesVo -> {
                updateUiState {
                    it.copy(hobbyInfoVo = pageVo)
                }
            }
        }
    }

    private fun signUpProcess() {
        if(isNetworkCall) return
        isNetworkCall = true

        getSignToken { token ->
            uploadProfile { url ->
                signUp(token, url)
            }
        }
    }

    private fun getSignToken(onSuccess:(String) -> Unit) {
        viewModelScope.launch {
            datsStoreUtil.getSignUpToken().collect { signToken ->
                inspectUiState(signToken, {
                    it?.let(onSuccess)
                }, { _, _ ->
                    isNetworkCall = false
                })
            }
        }
    }

    private fun uploadProfile(onSuccess:(String) -> Unit) {
        viewModelScope.launch {
            uiState.value.profileComposeVo.profileFile?.let { file ->
                val fileRequest = UploadFileRequestVo(UploadFileType.PROFILE, file)
                postUploadFileUseCase(fileRequest).collect { state ->
                    inspectUiState(state, {
                        onSuccess(it.fileUrl)
                    }) { _, _ ->
                        isNetworkCall = false
                    }
                }
            }?: onSuccess("")
        }
    }

    private fun signUp(profileUrl:String, signToken:String) {
        viewModelScope.launch {
            val request = uiState.value.getSignUpRequestVo(signToken, profileUrl)
            postSignUpUseCase(request).collect { state ->
                inspectUiState(state,::signUpSuccess) { _, individual ->
                    handleSignUpError(individual as SignUpError)
                    isNetworkCall = false
                }
            }
        }
    }

    private fun signUpSuccess(jwtResponseVo: PlubJwtResponseVo) {
        isNetworkCall = false
    }

    private fun handleSignUpError(signUpError: SignUpError) {
        isNetworkCall = false
        when (signUpError) {
            is SignUpError.DuplicatedEmail -> signUpErrorDialog("중복이메일")
            is SignUpError.DuplicatedNickname -> signUpErrorDialog("중복닉네임")
            is SignUpError.NotFoundCategory -> signUpErrorDialog("카테고리 못찾음")
            else -> Unit
        }
    }

    private fun signUpErrorDialog(string: String) {
        viewModelScope.launch {
            _showSignUpErrorDialog.emit(string)
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

    private fun isLastPage(currentPage: Int): Boolean = currentPage == SignUpPageType.values().size - 1

    private fun moveToPage(page: Int) {
        updateUiState { uiState ->
            uiState.copy(currentPage = page)
        }
    }
}