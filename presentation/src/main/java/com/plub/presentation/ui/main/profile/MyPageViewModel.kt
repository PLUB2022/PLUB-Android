package com.plub.presentation.ui.main.profile

import com.plub.presentation.base.BaseViewModel
import com.plub.presentation.ui.PageState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MyPageViewModel @Inject constructor() : BaseViewModel<PageState.Default>(PageState.Default) {
}