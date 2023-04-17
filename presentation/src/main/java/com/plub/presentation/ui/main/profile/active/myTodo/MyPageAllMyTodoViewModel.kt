package com.plub.presentation.ui.main.profile.active.myTodo

import androidx.lifecycle.viewModelScope
import com.plub.domain.model.enums.DialogMenuItemType
import com.plub.domain.model.enums.DialogMenuType
import com.plub.domain.model.enums.UploadFileType
import com.plub.domain.model.vo.media.UploadFileRequestVo
import com.plub.domain.model.vo.myPage.MyPageActiveRequestVo
import com.plub.domain.model.vo.myPage.MyPageToDoWithTitleVo
import com.plub.domain.model.vo.todo.TodoItemVo
import com.plub.domain.model.vo.todo.TodoProofRequestVo
import com.plub.domain.model.vo.todo.TodoRequestVo
import com.plub.domain.model.vo.todo.TodoTimelineVo
import com.plub.domain.usecase.*
import com.plub.presentation.base.BaseTestViewModel
import com.plub.presentation.parcelableVo.ParseTodoItemVo
import com.plub.presentation.util.PlubingInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@HiltViewModel
class MyPageAllMyTodoViewModel @Inject constructor(
    private val getMyToDoWithTitleUseCase: GetMyToDoWithTitleUseCase,
    private val putTodoCancelUseCase : PutTodoCancelUseCase,
    private val putTodoCompleteUseCase : PutTodoCompleteUseCase,
    private val postUploadFileUseCase : PostUploadFileUseCase,
    private val postTodoProofUseCase : PostTodoProofUseCase,
    private val putTodoLikeToggleUseCase : PutTodoLikeToggleUseCase
) : BaseTestViewModel<MyPageAllMyTodoState>() {

    private val todoListStateFlow: MutableStateFlow<List<TodoTimelineVo>> = MutableStateFlow(emptyList())

    override val uiState: MyPageAllMyTodoState = MyPageAllMyTodoState(
        todoListStateFlow.asStateFlow()
    )

    companion object{
        const val FIRST_CURSOR = 0
        const val FIRST_IDX = 0
    }

    private var cursorId = FIRST_CURSOR
    private var isNetworkCall: Boolean = false
    private var isLastPage: Boolean = false

    fun getMyToDoList() {
        cursorId = FIRST_CURSOR
        isNetworkCall = true
        viewModelScope.launch {
            getMyToDoWithTitleUseCase(MyPageActiveRequestVo(PlubingInfo.info.plubingId, cursorId)).collect{
                inspectUiState(it, ::handleGetMyToDoWithTitleSuccess)
            }
        }
    }

    private fun getMoreMyToDoList() {
        isNetworkCall = true
        viewModelScope.launch {
            getMyToDoWithTitleUseCase(MyPageActiveRequestVo(PlubingInfo.info.plubingId, cursorId)).collect{
                inspectUiState(it, ::handleGetMyToDoWithTitleSuccess)
            }
        }
    }

    private fun onFetchTodoList(){
        cursorUpdate()
        getMoreMyToDoList()
    }

    private fun cursorUpdate() {
        cursorId = if (uiState.todoList.value.isEmpty()) FIRST_CURSOR
        else uiState.todoList.value.lastOrNull()?.timelineId ?: FIRST_CURSOR
    }

    private fun handleGetMyToDoWithTitleSuccess(vo : MyPageToDoWithTitleVo){
        isNetworkCall = false
        isLastPage = vo.todoTimelineListVo.last
        updateTodoList(vo.todoTimelineListVo.content)
    }

    fun goToBack(){
        emitEventFlow(MyPageAllMyTodoEvent.GoToBack)
    }

    fun onClickTodoCheck(timelineId: Int, vo: TodoItemVo) {
        if(!vo.isAuthor) return

        if (vo.isChecked) cancelTodoCheck(timelineId, vo)
        else completeTodoCheck(timelineId, vo)
    }


    private fun cancelTodoCheck(timelineId: Int, vo: TodoItemVo) {
        putTodoCancel(vo.todoId) {
            val checkChangedVo = vo.copy(isChecked = !vo.isChecked)
            val rebasedTimelineList = getReplaceItemTodoRebasedList(timelineId, checkChangedVo, false)
            updateTodoList(rebasedTimelineList)
        }
    }

    private fun putTodoCancel(todoId: Int, onSuccess: () -> Unit) {
        val request = TodoRequestVo(PlubingInfo.info.plubingId, todoId = todoId)
        viewModelScope.launch {
            putTodoCancelUseCase(request).collect { state ->
                inspectUiState(state, {
                    onSuccess()
                })
            }
        }
    }

    private fun getReplaceItemTodoRebasedList(timelineId: Int, vo: TodoItemVo, isTop: Boolean): List<TodoTimelineVo> {
        return todoListStateFlow.value.toMutableList().apply {
            val timelinePosition = indexOfFirst { it.timelineId == timelineId }
            val timelineVo = get(timelinePosition)
            val rebasedTodoList = getTodoItemListRebaseItem(timelineVo.todoList,vo, isTop)
            val rebasedTimelineVo = timelineVo.copy(todoList = rebasedTodoList)
            set(timelinePosition, rebasedTimelineVo)
        }
    }

    private fun getTodoItemListRebaseItem(list: List<TodoItemVo>, vo: TodoItemVo, isTop: Boolean): List<TodoItemVo> {
        return list.toMutableList().apply {
            val removePosition = indexOfFirst { it.todoId == vo.todoId }
            removeAt(removePosition)
            if(isTop) add(FIRST_IDX, vo) else add(vo)
        }
    }

    private fun completeTodoCheck(timelineId: Int, vo: TodoItemVo) {
        putTodoComplete(vo.todoId) {
            val checkChangedVo = vo.copy(isChecked = !vo.isChecked)
            val rebasedTimelineList = getReplaceItemTodoRebasedList(timelineId, checkChangedVo, true)
            updateTodoList(rebasedTimelineList)
            showProofDialog(timelineId, vo)
        }
    }

    private fun putTodoComplete(todoId: Int, onSuccess: () -> Unit) {
        val request = TodoRequestVo(PlubingInfo.info.plubingId, todoId = todoId)
        viewModelScope.launch {
            putTodoCompleteUseCase(request).collect { state ->
                inspectUiState(state, {
                    onSuccess()
                })
            }
        }
    }

    private fun showProofDialog(timelineId: Int, todoItemVo: TodoItemVo) {
        val vo = ParseTodoItemVo.mapToParse(todoItemVo)
        emitEventFlow(MyPageAllMyTodoEvent.ShowTodoProofDialog(timelineId, vo))
    }

    private fun updateTodoList(todoList: List<TodoTimelineVo>) {
        viewModelScope.launch {
            todoListStateFlow.update { todoList }
        }
    }

    fun onClickProofComplete(timelineId: Int, todoId: Int, proofFile: File) {
        postUploadImage(proofFile) {
            postTodoProof(todoId, it) {
                val proofedList = getTodoListProofChanged(timelineId, todoId)
                updateTodoList(proofedList)
            }
        }
    }

    private fun postUploadImage(imageFile: File, onSuccess: (String) -> Unit) {
        val fileRequest = UploadFileRequestVo(UploadFileType.PLUBING_TODO, imageFile)
        viewModelScope.launch {
            postUploadFileUseCase(fileRequest).collect { state ->
                inspectUiState(state, { vo ->
                    onSuccess(vo.fileUrl)
                })
            }
        }
    }

    private fun postTodoProof(todoId: Int, imageUrl: String, onSuccess: () -> Unit) {
        val request = TodoProofRequestVo(PlubingInfo.info.plubingId, todoId, imageUrl)
        viewModelScope.launch {
            postTodoProofUseCase(request).collect { state ->
                inspectUiState(state, {
                    onSuccess()
                })
            }
        }
    }

    private fun getTodoListProofChanged(timelineId: Int, todoId: Int): List<TodoTimelineVo> {
        return todoListStateFlow.value.toMutableList().apply {
            val timelinePosition = indexOfFirst { it.timelineId == timelineId }
            val timelineVo = get(timelinePosition)
            val proofChangedList = getTodoItemListProofChanged(timelineVo.todoList, todoId)
            val proofChangedTimelineVo = timelineVo.copy(todoList = proofChangedList)
            set(timelinePosition, proofChangedTimelineVo)
        }
    }

    private fun getTodoItemListProofChanged(list: List<TodoItemVo>, todoId: Int): List<TodoItemVo> {
        return list.map {
            val isProofed = if (it.todoId == todoId) !it.isProof else it.isProof
            it.copy(isProof = isProofed)
        }
    }

    fun onClickTodoMenu(vo: TodoTimelineVo) {
        val menuType = when {
            vo.isAuthor -> DialogMenuType.TODO_LIST_AUTHOR_TYPE
            else -> DialogMenuType.TODO_LIST_COMMON_TYPE
        }
        emitEventFlow(MyPageAllMyTodoEvent.ShowMenuBottomSheetDialog(vo, menuType))
    }

    fun onClickMenuItemType(item: DialogMenuItemType, todoTimelineVo: TodoTimelineVo) {
        when (item) {
            DialogMenuItemType.TODO_PLANNER -> goToTodoPlanner(todoTimelineVo.date)
            else -> Unit
        }
    }

    private fun goToTodoPlanner(date:String) {
        emitEventFlow(MyPageAllMyTodoEvent.GoToPlannerTodo(date))
    }

    fun goToDefaultPlanner(){
        emitEventFlow(MyPageAllMyTodoEvent.GoToPlannerTodo(""))
    }

    fun onClickTodoLike(timelineId: Int) {
        putTodoLikeToggle(timelineId) {
            val replacedList = getTimelineListItemReplaced(timelineId, it)
            updateTodoList(replacedList)
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

    fun onClickTimeline(timelineId: Int) {
        emitEventFlow(MyPageAllMyTodoEvent.GoToDetailTodo(timelineId))
    }


    fun onScrollChanged() {
        if (!isLastPage && !isNetworkCall) onFetchTodoList()
    }
}