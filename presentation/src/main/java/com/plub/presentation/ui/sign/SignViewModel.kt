package com.plub.presentation.ui.sign

import com.plub.presentation.state.PageState
import com.plub.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class SignViewModel @Inject constructor() : BaseViewModel<PageState.Default>(PageState.Default) {

}