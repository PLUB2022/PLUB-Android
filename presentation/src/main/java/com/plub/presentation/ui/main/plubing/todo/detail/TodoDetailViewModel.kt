package com.plub.presentation.ui.main.plubing.todo.detail

import androidx.lifecycle.viewModelScope
import com.plub.domain.model.enums.DialogMenuItemType
import com.plub.domain.model.enums.DialogMenuType
import com.plub.domain.model.enums.UploadFileType
import com.plub.domain.model.vo.media.UploadFileRequestVo
import com.plub.domain.model.vo.todo.TodoItemVo
import com.plub.domain.model.vo.todo.TodoProofRequestVo
import com.plub.domain.model.vo.todo.TodoRequestVo
import com.plub.domain.model.vo.todo.TodoTimelineVo
import com.plub.domain.usecase.GetTodoDetailUseCase
import com.plub.domain.usecase.PostTodoProofUseCase
import com.plub.domain.usecase.PostUploadFileUseCase
import com.plub.domain.usecase.PutTodoCancelUseCase
import com.plub.domain.usecase.PutTodoCompleteUseCase
import com.plub.domain.usecase.PutTodoLikeToggleUseCase
import com.plub.presentation.base.BaseTestViewModel
import com.plub.presentation.parcelableVo.ParseTodoItemVo
import com.plub.presentation.util.PlubingInfo
import com.plub.presentation.util.ResourceProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject
import kotlin.properties.Delegates

@HiltViewModel
class TodoDetailViewModel @Inject constructor(
    val resourceProvider: ResourceProvider,
    val putTodoCancelUseCase: PutTodoCancelUseCase,
    val putTodoCompleteUseCase: PutTodoCompleteUseCase,
    val postTodoProofUseCase: PostTodoProofUseCase,
    val postUploadFileUseCase: PostUploadFileUseCase,
    val getTodoDetailUseCase: GetTodoDetailUseCase,
    val putTodoLikeToggleUseCase: PutTodoLikeToggleUseCase,
) : BaseTestViewModel<TodoDetailPageState>() {

    companion object {
        private const val FIRST_INDEX = 0
    }

    private var plubingId = PlubingInfo.info.plubingId
    private var timelineId by Delegates.notNull<Int>()

    private val todoListStateFlow: MutableStateFlow<List<TodoItemVo>> = MutableStateFlow(emptyList())
    private val profileImageStateFlow: MutableStateFlow<String> = MutableStateFlow("")
    private val dateStateFlow: MutableStateFlow<String> = MutableStateFlow("")
    private val nicknameStateFlow: MutableStateFlow<String> = MutableStateFlow("")
    private val likeCountStateFlow: MutableStateFlow<String> = MutableStateFlow("")
    private val isLikeStateFlow: MutableStateFlow<Boolean> = MutableStateFlow(false)

    override val uiState: TodoDetailPageState = TodoDetailPageState(
        todoListStateFlow.asStateFlow(),
        profileImageStateFlow.asStateFlow(),
        dateStateFlow.asStateFlow(),
        nicknameStateFlow.asStateFlow(),
        likeCountStateFlow.asStateFlow(),
        isLikeStateFlow.asStateFlow()
    )

    fun initArgs(timelineId: Int) {
        this.timelineId = timelineId
    }

    fun onGetTodoDetail() {
        if(todoListStateFlow.value.isNotEmpty()) return
        getTodoDetail {
            timelineVoUpdate(it)
        }
    }

    fun onClickTodoLike() {
        putTodoLikeToggle(timelineId) {
            timelineVoUpdate(it)
        }
    }

    fun onClickTodoCheck(vo: TodoItemVo) {
        if (vo.isChecked) cancelTodoCheck(vo) else completeTodoCheck(vo)
    }

    fun onClickProofComplete(todoId: Int, proofFile: File) {
        postUploadImage(proofFile) {
            postTodoProof(todoId, it) {
                val proofedList = getTodoListProofChanged(todoId)
                updateTodoList(proofedList)
            }
        }
    }

    fun onClickTodoMenu(vo: TodoItemVo) {
        val menuType = when {
            vo.isAuthor && vo.isChecked && !vo.isProof -> DialogMenuType.TODO_DETAIL_AUTHOR_CHECKED_NOT_PROOFED_TYPE
            vo.isAuthor -> DialogMenuType.TODO_DETAIL_AUTHOR_COMMON_TYPE
            else -> DialogMenuType.TODO_DETAIL_COMMON_TYPE
        }
        emitEventFlow(TodoDetailEvent.ShowMenuBottomSheetDialog(menuType, vo))
    }

    fun onClickMenuItemType(item: DialogMenuItemType, todoItemVo: TodoItemVo) {
        when (item) {
            DialogMenuItemType.TODO_PROOF -> showProofDialog(todoItemVo)
            DialogMenuItemType.TODO_PLANNER -> goToTodoPlanner(todoItemVo.date)
            DialogMenuItemType.TODO_REPORT -> goToTodoReport()
            else -> Unit
        }
    }

    private fun getTodoDetail(onSuccess: (TodoTimelineVo) -> Unit) {
        val request = TodoRequestVo(plubingId, timelineId = timelineId)
        viewModelScope.launch {
            getTodoDetailUseCase(request).collect { state ->
                inspectUiState(state,onSuccess)
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

    private fun cancelTodoCheck(vo: TodoItemVo) {
        putTodoCancel(vo.todoId) {
            checkChangeRebaseUpdate(vo, false)
        }
    }

    private fun completeTodoCheck(vo: TodoItemVo) {
        putTodoComplete(vo.todoId) {
            checkChangeRebaseUpdate(vo, true)
            showProofDialog(vo)
        }
    }

    private fun showProofDialog(todoItemVo: TodoItemVo) {
        val vo = ParseTodoItemVo.mapToParse(todoItemVo)
        emitEventFlow(TodoDetailEvent.ShowTodoProofDialog(vo))
    }

    private fun checkChangeRebaseUpdate(vo: TodoItemVo, isTop: Boolean) {
        val checkChangedVo = vo.copy(isChecked = !vo.isChecked)
        val rebasedList = getTodoListRebaseItem(checkChangedVo, isTop)
        updateTodoList(rebasedList)
    }

    private fun getTodoListRebaseItem(vo: TodoItemVo, isTop: Boolean): List<TodoItemVo> {
        return uiState.todoList.value.toMutableList().apply {
            val removePosition = indexOfFirst { it.todoId == vo.todoId }
            removeAt(removePosition)
            if(isTop) add(FIRST_INDEX, vo) else add(vo)
        }
    }

    private fun getTodoListProofChanged(todoId: Int): List<TodoItemVo> {
        return uiState.todoList.value.map {
            val isProofed = if (it.todoId == todoId) !it.isProof else it.isProof
            it.copy(isProof = isProofed)
        }
    }

    private fun goToTodoPlanner(date:String) {
        emitEventFlow(TodoDetailEvent.GoToPlannerTodo(date))
    }

    private fun goToTodoReport() {

    }

    private fun timelineVoUpdate(timelineVo: TodoTimelineVo) {
        updateTodoList(timelineVo.todoList)
        updateProfileImage(timelineVo.accountInfoVo.profileImage)
        updateDate(timelineVo.todoList.first().date)
        updateNickname(timelineVo.accountInfoVo.nickname)
        updateLikeCount(timelineVo.totalLikes.toString())
        updateIsLike(timelineVo.isLike)
    }

    private fun updateTodoList(list:List<TodoItemVo>) {
        todoListStateFlow.update { list }
    }

    private fun updateProfileImage(image: String) {
        profileImageStateFlow.update { image }
    }

    private fun updateDate(date: String) {
        dateStateFlow.update { date }
    }

    private fun updateNickname(nickname: String) {
        nicknameStateFlow.update { nickname }
    }

    private fun updateLikeCount(count: String) {
        likeCountStateFlow.update { count }
    }

    private fun updateIsLike(isLike: Boolean) {
        isLikeStateFlow.update { isLike }
    }
}
