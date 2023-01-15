package com.plub.presentation.ui.sign.welcome

import com.plub.presentation.base.BaseViewModel
import com.plub.presentation.event.WelcomeEvent
import com.plub.presentation.state.PageState
import com.plub.presentation.util.ResourceProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class WelcomeViewModel @Inject constructor(
    val resourceProvider: ResourceProvider,
) : BaseViewModel<PageState.Default>(PageState.Default) {

    fun onClickPlubStartButton() {
        emitEventFlow(WelcomeEvent.GoToMain)
    }
}