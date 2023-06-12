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
import com.plub.domain.usecase.GetTimelineListUseCase
import com.plub.domain.usecase.PostTodoProofUseCase
import com.plub.domain.usecase.PostUploadFileUseCase
import com.plub.domain.usecase.PutTodoCancelUseCase
import com.plub.domain.usecase.PutTodoCompleteUseCase
import com.plub.domain.usecase.PutTodoLikeToggleUseCase
import com.plub.presentation.base.BaseTestViewModel
import com.plub.presentation.parcelableVo.ParseTodoItemVo
import com.plub.presentation.ui.main.plubing.board.PlubingBoardViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject
import kotlin.properties.Delegates

@HiltViewModel
class PlubingTodoViewModel @Inject constructor(
    val getTimelineListUseCase: GetTimelineListUseCase,
    val postTodoProofUseCase: PostTodoProofUseCase,
    val postUploadFileUseCase: PostUploadFileUseCase,
    val putTodoCompleteUseCase: PutTodoCompleteUseCase,
    val putTodoCancelUseCase: PutTodoCancelUseCase,
    val putTodoLikeToggleUseCase: PutTodoLikeToggleUseCase,
) : BaseTestViewModel<PlubingTodoPageState>() {

    private val todoListStateFlow: MutableStateFlow<List<TodoTimelineVo>> = MutableStateFlow(emptyList())

    override val uiState: PlubingTodoPageState = PlubingTodoPageState(
        todoListStateFlow.asStateFlow()
    )

    companion object {
        private const val GOAL_TYPE_POSITION = 0
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
        if(todoListStateFlow.value.isNotEmpty()) return
        isNetworkCall = true
        isLastPage = false
        cursorId = FIRST_CURSOR
        getTodoList(showLoading = true)
    }

    fun onScrollChanged() {
        if (!isLastPage && !isNetworkCall) onGetNextTodoList()
    }

    fun onClickTodoCheck(timelineId: Int, vo: TodoItemVo) {
        if(!vo.isAuthor) return

        if (vo.isChecked) cancelTodoCheck(timelineId, vo)
        else completeTodoCheck(timelineId, vo)
    }

    fun onClickTodoMenu(vo: TodoTimelineVo) {
        val menuType = when {
            vo.isAuthor -> DialogMenuType.TODO_LIST_AUTHOR_TYPE
            else -> DialogMenuType.TODO_LIST_COMMON_TYPE
        }
        emitEventFlow(PlubingTodoEvent.ShowMenuBottomSheetDialog(vo, menuType))
    }

    fun onClickTimeline(timelineId: Int) {
        emitEventFlow(PlubingTodoEvent.GoToDetailTodo(timelineId))
    }

    fun onClickTodoLike(timelineId: Int) {
        putTodoLikeToggle(timelineId) {
            val replacedList = getTimelineListItemReplaced(timelineId, it)
            updateTodoList(replacedList)
        }
    }

    fun onClickMenuItemType(item: DialogMenuItemType, todoTimelineVo: TodoTimelineVo) {
        when (item) {
            DialogMenuItemType.TODO_PLANNER -> goToTodoPlanner(todoTimelineVo.date)
            DialogMenuItemType.TODO_REPORT -> goToTodoReport(todoTimelineVo)
            else -> Unit
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

    private fun onGetNextTodoList() {
        isNetworkCall = true
        cursorUpdate()
        getTodoList(showLoading = false)
    }

    private fun getTodoList(showLoading : Boolean) {
        viewModelScope.launch {
            val requestVo = TodoGetTimelineRequestVo(plubbingId = plubingId, cursorId = cursorId)
            getTimelineListUseCase(requestVo).collect {
                inspectUiState(it, ::successGetTodoList, needShowLoading = showLoading)
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
        val request = TodoProofRequestVo(plubingId, todoId, imageUrl)
        viewModelScope.launch {
            postTodoProofUseCase(request).collect { state ->
                inspectUiState(state, {
                    onSuccess()
                })
            }
        }
    }

    private fun putTodoComplete(todoId: Int, onSuccess: () -> Unit) {
        val request = TodoRequestVo(plubingId, todoId = todoId)
        viewModelScope.launch {
            putTodoCompleteUseCase(request).collect { state ->
                inspectUiState(state, {
                    onSuccess()
                })
            }
        }
    }

    private fun putTodoCancel(todoId: Int, onSuccess: () -> Unit) {
        val request = TodoRequestVo(plubingId, todoId = todoId)
        viewModelScope.launch {
            putTodoCancelUseCase(request).collect { state ->
                inspectUiState(state, {
                    onSuccess()
                })
            }
        }
    }

    private fun putTodoLikeToggle(timelineId: Int, onSuccess: (TodoTimelineVo) -> Unit) {
        val request = TodoRequestVo(plubingId, timelineId = timelineId)
        viewModelScope.launch {
            putTodoLikeToggleUseCase(request).collect { state ->
                inspectUiState(state, onSuccess)
            }
        }
    }

    private fun successGetTodoList(vo: TodoTimelineListVo) {
        isLastPage = vo.last
        val lastDate = todoListStateFlow.value.lastOrNull()?.date
        val groupByDateList = todoGroupByDate(vo.content, lastDate)
        val mergedList = if(isLastPage) getMergeList(groupByDateList) else getMergeList(groupByDateList) + listOf(TodoTimelineVo(viewType = TodoTimelineViewType.LOADING))
        updateTodoList(mergedList)
        isNetworkCall = false
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
        val originList = todoListStateFlow.value.filterNot { it.viewType == TodoTimelineViewType.LOADING }
        return if (cursorId == FIRST_CURSOR) list.toMutableList().apply {
            add(GOAL_TYPE_POSITION, TodoTimelineVo(viewType = TodoTimelineViewType.GOAL))
        } else originList + list
    }

    private fun cursorUpdate() {
        cursorId = if (todoListStateFlow.value.isEmpty()) FIRST_CURSOR
        else todoListStateFlow.value.filterNot { it.viewType == TodoTimelineViewType.LOADING }.lastOrNull { it.viewType == TodoTimelineViewType.PLUBING }?.timelineId ?: FIRST_CURSOR
    }

    private fun completeTodoCheck(timelineId: Int, vo: TodoItemVo) {
        putTodoComplete(vo.todoId) {
            val checkChangedVo = vo.copy(isChecked = !vo.isChecked)
            val rebasedTimelineList = getReplaceItemTodoRebasedList(timelineId, checkChangedVo, true)
            updateTodoList(rebasedTimelineList)
            showProofDialog(timelineId, vo)
        }
    }

    private fun cancelTodoCheck(timelineId: Int, vo: TodoItemVo) {
        putTodoCancel(vo.todoId) {
            val checkChangedVo = vo.copy(isChecked = !vo.isChecked)
            val rebasedTimelineList = getReplaceItemTodoRebasedList(timelineId, checkChangedVo, false)
            updateTodoList(rebasedTimelineList)
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

    private fun showProofDialog(timelineId: Int, todoItemVo: TodoItemVo) {
        val vo = ParseTodoItemVo.mapToParse(todoItemVo)
        emitEventFlow(PlubingTodoEvent.ShowTodoProofDialog(timelineId, vo))
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

    private fun getTimelineListItemReplaced(timelineId: Int, timelineVo: TodoTimelineVo): List<TodoTimelineVo> {
        return todoListStateFlow.value.map {
            if(it.timelineId == timelineId) timelineVo else it.copy()
        }
    }

    private fun goToTodoPlanner(date:String) {
        emitEventFlow(PlubingTodoEvent.GoToPlannerTodo(date))
    }

    private fun goToTodoReport(todoTimelineVo: TodoTimelineVo) {

    }

    private fun updateTodoList(todoList: List<TodoTimelineVo>) {
        viewModelScope.launch {
            todoListStateFlow.update { todoList }
        }
    }
}
