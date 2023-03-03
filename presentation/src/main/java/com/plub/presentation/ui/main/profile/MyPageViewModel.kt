package com.plub.presentation.ui.main.profile

import com.plub.presentation.base.BaseViewModel
import com.plub.presentation.ui.PageState
import dagger.hilt.android.lifecycle.HiltViewModel

@HiltViewModel
class MyPageViewModel : BaseViewModel<PageState.Default>(PageState.Default) {
}