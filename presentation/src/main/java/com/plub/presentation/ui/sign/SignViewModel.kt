package com.plub.presentation.ui.sign

import com.plub.domain.model.state.OnboardingPageState
import com.plub.domain.model.state.PageState
import com.plub.presentation.base.BaseViewModel
import com.plub.presentation.util.ResourceProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class SignViewModel @Inject constructor() : BaseViewModel<PageState.Default>(PageState.Default) {

}