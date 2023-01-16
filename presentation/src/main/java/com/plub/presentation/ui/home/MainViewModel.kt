package com.plub.presentation.ui.home

import com.plub.presentation.base.BaseViewModel
import com.plub.presentation.state.PageState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor() : BaseViewModel<PageState.Default>(PageState.Default) {

}
