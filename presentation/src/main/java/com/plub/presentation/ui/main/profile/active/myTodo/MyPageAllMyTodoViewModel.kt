package com.plub.presentation.ui.main.profile.active.myTodo

import androidx.lifecycle.viewModelScope
import com.plub.domain.model.vo.myPage.MyPageActiveRequestVo
import com.plub.domain.model.vo.myPage.MyPageToDoWithTitleVo
import com.plub.domain.usecase.GetMyToDoWithTitleUseCase
import com.plub.presentation.base.BaseViewModel
import com.plub.presentation.util.PlubingInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyPageAllMyTodoViewModel @Inject constructor(
    private val getMyToDoWithTitleUseCase: GetMyToDoWithTitleUseCase,
) : BaseViewModel<MyPageAllMyTodoState>(MyPageAllMyTodoState()) {

    companion object{
        const val FIRST_CURSOR = 0
    }

    private var cursorId = FIRST_CURSOR

    fun getMyToDoList() {
        viewModelScope.launch {
            getMyToDoWithTitleUseCase(MyPageActiveRequestVo(PlubingInfo.info.plubingId, cursorId)).collect{
                inspectUiState(it, ::handleGetMyToDoWithTitleSuccess)
            }
        }
    }

    private fun handleGetMyToDoWithTitleSuccess(vo : MyPageToDoWithTitleVo){
        updateUiState { uiState ->
            uiState.copy(
                todoList = vo.todoTimelineListVo.content
            )
        }
    }

    fun goToBack(){
        emitEventFlow(MyPageAllMyTodoEvent.GoToBack)
    }

}