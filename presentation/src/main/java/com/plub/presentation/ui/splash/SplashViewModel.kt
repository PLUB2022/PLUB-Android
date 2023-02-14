package com.plub.presentation.ui.splash

import com.plub.domain.usecase.FetchPlubRefreshTokenUseCase
import com.plub.domain.usecase.PostReIssueTokenUseCase
import com.plub.presentation.base.BaseViewModel
import com.plub.presentation.ui.PageState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val reIssueTokenUseCase: PostReIssueTokenUseCase
) : BaseViewModel<PageState.Default>(PageState.Default) {
}