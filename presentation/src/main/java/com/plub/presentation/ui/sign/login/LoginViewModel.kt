package com.plub.presentation.ui.sign.login

import com.plub.domain.model.state.OnboardingPageState
import com.plub.domain.model.state.PageState
import com.plub.domain.model.vo.onboarding.OnboardingItemVo
import com.plub.presentation.R
import com.plub.presentation.base.BaseViewModel
import com.plub.presentation.util.ResourceProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val resourceProvider: ResourceProvider,
) : BaseViewModel<PageState.Default>(PageState.Default) {

}