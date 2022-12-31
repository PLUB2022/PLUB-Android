package com.plub.presentation.state

import com.plub.domain.UiState
import com.plub.domain.model.vo.login.SampleLogin

data class SampleLoginPageState(
    val loginData: UiState<SampleLogin> = UiState.Loading
): PageState