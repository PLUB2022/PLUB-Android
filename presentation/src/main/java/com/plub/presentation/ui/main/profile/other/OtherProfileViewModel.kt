package com.plub.presentation.ui.main.profile.other

import androidx.lifecycle.viewModelScope
import com.plub.domain.model.enums.DialogMenuItemType
import com.plub.domain.model.enums.DialogMenuType
import com.plub.domain.model.vo.account.MyInfoResponseVo
import com.plub.domain.model.vo.todo.GetOtherTodoRequestVo
import com.plub.domain.model.vo.todo.TodoRequestVo
import com.plub.domain.model.vo.todo.TodoTimelineListVo
import com.plub.domain.model.vo.todo.TodoTimelineVo
import com.plub.domain.usecase.GetOtherProfileUseCase
import com.plub.domain.usecase.GetOtherTodoUseCase
import com.plub.domain.usecase.PutTodoLikeToggleUseCase
import com.plub.presentation.base.BaseTestViewModel
import com.plub.presentation.util.PlubingInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OtherProfileViewModel @Inject constructor(
    private val getOtherProfileUseCase: GetOtherProfileUseCase,
    private val getOtherTodoUseCase: GetOtherTodoUseCase,
    private val putTodoLikeToggleUseCase : PutTodoLikeToggleUseCase
) : BaseTestViewModel<OtherProfileState>() {

    companion object{
        const val FIRST_CURSOR = 0
    }

    private val profileStateFlow : MutableStateFlow<MyInfoResponseVo> = MutableStateFlow(
        MyInfoResponseVo()
    )
    private val todoListStateFlow : MutableStateFlow<List<TodoTimelineVo>> = MutableStateFlow(
        emptyList()
    )
    override val uiState: OtherProfileState = OtherProfileState(
        profile = profileStateFlow.asStateFlow(),
        todoList = todoListStateFlow.asStateFlow()
    )

    private var isLast : Boolean = false
    private var cursorId : Int = FIRST_CURSOR

    fun fetchOtherProfile(nickName : String){
        viewModelScope.launch {
            getOtherProfileUseCase(nickName).collect{
                inspectUiState(it, ::onSuccessGetOtherInfo)
            }
        }
    }

    fun fetchOtherTodo(plubbingId: Int, accountId: Int){
        viewModelScope.launch {
            val request = GetOtherTodoRequestVo(
                plubbingId, accountId, cursorId
            )
            getOtherTodoUseCase(request).collect{
                inspectUiState(it, ::handleGetOtherTodoSuccess)
            }
        }
    }

    private fun onSuccessGetOtherInfo(vo : MyInfoResponseVo){
        viewModelScope.launch {
            profileStateFlow.update { vo }
        }
    }

    private fun handleGetOtherTodoSuccess(data: TodoTimelineListVo) {
        isLast = data.last
        updateOtherTodoList(data.content)
    }

    private fun updateOtherTodoList(list : List<TodoTimelineVo>){
        viewModelScope.launch {
            todoListStateFlow.update { list }
        }
    }

    fun onClickTodoMenu(vo: TodoTimelineVo) {
        val menuType = when {
            vo.isAuthor -> DialogMenuType.TODO_LIST_AUTHOR_TYPE
            else -> DialogMenuType.TODO_LIST_COMMON_TYPE
        }
        emitEventFlow(OtherProfileEvent.ShowMenuBottomSheetDialog(vo, menuType))
    }

    fun onClickTodoLike(timelineId: Int) {
        putTodoLikeToggle(timelineId) {
            val replacedList = getTimelineListItemReplaced(timelineId, it)
            updateOtherTodoList(replacedList)
        }
    }

    private fun putTodoLikeToggle(timelineId: Int, onSuccess: (TodoTimelineVo) -> Unit) {
        val request = TodoRequestVo(PlubingInfo.info.plubingId, timelineId = timelineId)
        viewModelScope.launch {
            putTodoLikeToggleUseCase(request).collect { state ->
                inspectUiState(state, onSuccess)
            }
        }
    }

    private fun getTimelineListItemReplaced(timelineId: Int, timelineVo: TodoTimelineVo): List<TodoTimelineVo> {
        return todoListStateFlow.value.map {
            if(it.timelineId == timelineId) timelineVo else it.copy()
        }
    }

    fun onClickMenuItemType(item: DialogMenuItemType, todoTimelineVo: TodoTimelineVo) {
        when (item) {
            DialogMenuItemType.TODO_REPORT -> TODO()
            else -> { }
        }
    }

    fun onClickClose(){
        emitEventFlow(OtherProfileEvent.CloseButtonClick)
    }
}
