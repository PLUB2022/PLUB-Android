package com.plub.presentation.ui.main.plubing.todo

import com.plub.presentation.base.BaseViewModel
import com.plub.presentation.ui.PageState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PlubingTodoViewModel @Inject constructor(
) : BaseViewModel<PageState.Default>(PageState.Default) {

}
