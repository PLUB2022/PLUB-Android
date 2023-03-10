package com.plub.presentation.ui.main.plubing.todo

import androidx.lifecycle.viewModelScope
import com.plub.domain.model.enums.DialogMenuItemType
import com.plub.domain.model.enums.DialogMenuType
import com.plub.domain.model.enums.TodoTimelineViewType
import com.plub.domain.model.enums.UploadFileType
import com.plub.domain.model.vo.media.UploadFileRequestVo
import com.plub.domain.model.vo.todo.TodoGetTimelineRequestVo
import com.plub.domain.model.vo.todo.TodoItemVo
import com.plub.domain.model.vo.todo.TodoProofRequestVo
import com.plub.domain.model.vo.todo.TodoRequestVo
import com.plub.domain.model.vo.todo.TodoTimelineListVo
import com.plub.domain.model.vo.todo.TodoTimelineVo
import com.plub.domain.usecase.PutTodoCompleteUseCase
import com.plub.domain.usecase.GetTodoListUseCase
import com.plub.domain.usecase.PostTodoProofUseCase
import com.plub.domain.usecase.PostUploadFileUseCase
import com.plub.domain.usecase.PutTodoCancelUseCase
import com.plub.presentation.base.BaseViewModel
import com.plub.presentation.parcelableVo.ParseTodoItemVo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject
import kotlin.properties.Delegates

@HiltViewModel
class PlubingTodoViewModel @Inject constructor(
    val getTodoListUseCase: GetTodoListUseCase,
    val postTodoProofUseCase: PostTodoProofUseCase,
    val postUploadFileUseCase: PostUploadFileUseCase,
    val putTodoCompleteUseCase: PutTodoCompleteUseCase,
    val putTodoCancelUseCase: PutTodoCancelUseCase,
) : BaseViewModel<PlubingTodoPageState>(PlubingTodoPageState()) {

    companion object {
        private const val FIRST_CURSOR = 0
        private const val FIRST_IDX = 0
    }

    private var plubingId by Delegates.notNull<Int>()
    private var isNetworkCall: Boolean = false
    private var isLastPage: Boolean = false
    private var cursorId: Int = FIRST_CURSOR

    fun initPlubingId(plubingId: Int) {
        this.plubingId = plubingId
    }

    fun onGetTodoList() {
        isNetworkCall = true
        cursorUpdate()
        getTodoList()
    }

    fun onScrollChanged(isBottom: Boolean, isDownScroll: Boolean) {
        if (isBottom && isDownScroll && !isLastPage && !isNetworkCall) onGetTodoList()
    }

    fun onClickTodoCheck(timelineId: Int, vo: TodoItemVo) {
//        if(!vo.isAuthor) return

        if (vo.isChecked) cancelTodoCheck(timelineId,vo.todoId)
        else completeTodoCheck(timelineId, vo)
    }

    fun onClickTodoMenu(vo: TodoTimelineVo) {
        val menuType = when {
            vo.isAuthor -> DialogMenuType.TODO_LIST_AUTHOR_TYPE
            else -> DialogMenuType.TODO_LIST_COMMON_TYPE
        }
        emitEventFlow(PlubingTodoEvent.ShowMenuBottomSheetDialog(vo, menuType))
    }

    fun onClickMenuItemType(item: DialogMenuItemType, todoTimelineVo: TodoTimelineVo) {
        when (item) {
            DialogMenuItemType.TODO_MANAGE -> goToTodoManage(todoTimelineVo)
            DialogMenuItemType.TODO_REPORT -> goToTodoReport(todoTimelineVo)
            else -> Unit
        }
    }

    fun onClickProofComplete(todoId: Int, proofFile: File) {
        postUploadImage(proofFile) {
            postTodoProof(todoId, it) {

            }
        }
    }

    private fun getTodoList() {
        viewModelScope.launch {
            val requestVo = TodoGetTimelineRequestVo(plubbingId = plubingId, cursorId = cursorId)
            getTodoListUseCase(requestVo).collect {
                inspectUiState(it, ::successGetTodoList)
            }
        }
    }

    private fun successGetTodoList(vo: TodoTimelineListVo) {
        isLastPage = vo.last
        val lastDate = uiState.value.todoList.lastOrNull()?.date
        val groupByDateList = todoGroupByDate(vo.content, lastDate)
        updateTodoTimelineList(getMergeList(groupByDateList))
    }

    private fun todoGroupByDate(
        list: List<TodoTimelineVo>,
        lastDate: String?
    ): List<TodoTimelineVo> {
        return list.groupBy { it.date }.mapValues {
            val isExistDate = lastDate == it.key
            val dateItem = TodoTimelineVo(TodoTimelineViewType.DATE, date = it.key)
            it.value.toMutableList().apply {
                if (!isExistDate) add(FIRST_IDX, dateItem)
            }
        }.flatMap { it.value }
    }

    private fun getMergeList(list: List<TodoTimelineVo>): List<TodoTimelineVo> {
        val originList = uiState.value.todoList
        return if (cursorId == FIRST_CURSOR) list else originList + list
    }

    private fun updateTodoTimelineList(list: List<TodoTimelineVo>) {
        updateUiState { uiState ->
            uiState.copy(
                todoList = list
            )
        }
        isNetworkCall = false
    }

    private fun cursorUpdate() {
        cursorId = if (uiState.value.todoList.isEmpty()) FIRST_CURSOR
        else uiState.value.todoList.lastOrNull { it.viewType == TodoTimelineViewType.PLUBING }?.timelineId
            ?: FIRST_CURSOR
    }

    private fun showProofDialog(todoItemVo: TodoItemVo) {
        val vo = ParseTodoItemVo.mapToParse(todoItemVo)
        emitEventFlow(PlubingTodoEvent.ShowTodoProofDialog(vo))
    }

    private fun cancelTodoCheck(timelineId: Int, todoId: Int) {
        putTodoCancel(todoId) {
            updateTodoCheckChange(timelineId, todoId)
        }
    }

    private fun completeTodoCheck(timelineId: Int, vo: TodoItemVo) {
        getTodoComplete(vo.todoId) {
            updateTodoCheckChange(timelineId, vo.todoId)
            showProofDialog(vo)
        }
    }

    private fun updateTodoCheckChange(timelineId: Int, todoId: Int) {
        updateUiState { uiState ->
            uiState.copy(
                todoList = getTodoListCheckChanged(timelineId, todoId)
            )
        }
    }

    private fun getTodoListCheckChanged(timelineId: Int, todoId: Int): List<TodoTimelineVo> {
        return uiState.value.todoList.map {
            val todoItemList = if (it.timelineId == timelineId) getTodoItemListCheckChanged(it.todoList, todoId) else it.todoList
            it.copy(todoList = todoItemList)
        }
    }

    private fun getTodoItemListCheckChanged(list: List<TodoItemVo>, todoId: Int): List<TodoItemVo> {
        return list.map {
            val isChecked = if (it.todoId == todoId) !it.isChecked else it.isChecked
            it.copy(isChecked = isChecked)
        }
    }

    fun goToTodoReport(todoTimelineVo: TodoTimelineVo) {

    }

    private fun goToTodoManage(todoTimelineVo: TodoTimelineVo) {

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
        val request = TodoProofRequestVo(plubingId, todoId, imageUrl)
        viewModelScope.launch {
            postTodoProofUseCase(request).collect { state ->
                inspectUiState(state, {
                    onSuccess()
                })
            }
        }
    }

    private fun getTodoComplete(todoId: Int, onSuccess: () -> Unit) {
        val request = TodoRequestVo(plubingId, todoId)
        viewModelScope.launch {
            putTodoCompleteUseCase(request).collect { state ->
                inspectUiState(state, {
                    onSuccess()
                })
            }
        }
    }

    private fun putTodoCancel(todoId: Int, onSuccess: () -> Unit) {
        val request = TodoRequestVo(plubingId, todoId)
        viewModelScope.launch {
            putTodoCancelUseCase(request).collect { state ->
                inspectUiState(state, {
                    onSuccess()
                })
            }
        }
    }
}
