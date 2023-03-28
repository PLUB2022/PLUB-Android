package com.plub.presentation.ui.main.profile.active.myTodo

import com.plub.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MyPageAllMyTodoViewModel @Inject constructor(
) : BaseViewModel<MyPageAllMyTodoState>(MyPageAllMyTodoState()) {

}