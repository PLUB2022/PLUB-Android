package com.plub.domain.model.state

import com.plub.domain.UiState
import com.plub.domain.model.SampleLogin

data class SampleLoginPageState(
    val loginData: UiState<SampleLogin> = UiState.Loading
): PageState