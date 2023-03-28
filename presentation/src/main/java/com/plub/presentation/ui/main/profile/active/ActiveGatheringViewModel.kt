package com.plub.presentation.ui.main.profile.active

import androidx.lifecycle.viewModelScope
import com.plub.domain.model.enums.*
import com.plub.domain.model.vo.board.PlubingBoardListVo
import com.plub.domain.model.vo.board.PlubingBoardVo
import com.plub.domain.model.vo.media.UploadFileRequestVo
import com.plub.domain.model.vo.myPage.MyPageActiveDetailVo
import com.plub.domain.model.vo.myPage.MyPageActiveRequestVo
import com.plub.domain.model.vo.myPage.MyPageDetailTitleVo
import com.plub.domain.model.vo.myPage.MyPageToDoWithTitleVo
import com.plub.domain.model.vo.todo.*
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
class ActiveGatheringViewModel @Inject constructor(
    private val getMyPostUseCase: GetMyPostUseCase,
    private val getMyToDoWithTitleUseCase: GetMyToDoWithTitleUseCase,
    private val putTodoCompleteUseCase: PutTodoCompleteUseCase,
    private val postUploadFileUseCase: PostUploadFileUseCase,
    private val postTodoProofUseCase: PostTodoProofUseCase,
    private val putTodoCancelUseCase: PutTodoCancelUseCase,
) : BaseTestViewModel<ActiveGatheringPageState>() {

    private val detailListStateFlow: MutableStateFlow<List<MyPageActiveDetailVo>> = MutableStateFlow(emptyList())

    override val uiState: ActiveGatheringPageState = ActiveGatheringPageState(
        detailListStateFlow.asStateFlow()
    )

    companion object {
        const val FIRST_CURSOR = 0
        const val FIRST_IDX = 0
        const val MAX_SHOW_COUNT = 2
    }

    private var cursorId: Int = FIRST_CURSOR
    private var plubingId: Int = 0
    private var gatheringMyType : MyPageGatheringMyType = MyPageGatheringMyType.END

    fun setPlubIdAndStateType(id: Int, type: MyPageGatheringMyType) {
        plubingId = id
        gatheringMyType = type
    }

    fun setView() {
        viewModelScope.launch {
            getMyToDoWithTitleUseCase(MyPageActiveRequestVo(plubingId, cursorId)).collect{
                inspectUiState(it, ::handleGetMyToDoWithTitleSuccess)
            }

            getMyPostUseCase(MyPageActiveRequestVo(plubingId, cursorId)).collect {
                inspectUiState(it, ::handleGetMyPostSuccess)
            }
        }
    }

    private fun handleGetMyToDoWithTitleSuccess(vo : MyPageToDoWithTitleVo){
        updateTopView(vo.titleVo)
        updatePlubbingInfo(vo.titleVo)
        updateToDoView(vo.todoTimelineListVo)
    }

    private fun updateTopView(top : MyPageDetailTitleVo){
        val topView = top.copy(
            viewType = gatheringMyType
        )
        updateDetailList(getMergedTopList(topView))
    }

    private fun getMergedTopList(view : MyPageDetailTitleVo) : List<MyPageActiveDetailVo>{
        return listOf(
            MyPageActiveDetailVo(
                viewType = MyPageActiveDetailViewType.TOP,
                title = view
            )
        )
    }

    private fun updatePlubbingInfo(plubbingVo : MyPageDetailTitleVo){
        PlubingInfo.updateInfo(
            PlubingInfo.info.copy(
                plubingId = plubbingVo.plubbingId,
                name = plubbingVo.title,
                placeName = plubbingVo.position,
                time = plubbingVo.time,
                days = plubbingVo.date
            )
        )
    }

    private fun updateToDoView(todoList : TodoTimelineListVo){
        val originList = uiState.detailList.value
        val mergedList = if (todoList.content.size > MAX_SHOW_COUNT) {
            setListOverToDoMaxCount(todoList.content)
        } else {
            setListUnderToDoMaxCount(todoList.content)
        }

        updateDetailList(originList + mergedList)
    }

    private fun setListOverToDoMaxCount(list: List<TodoTimelineVo>) : List<MyPageActiveDetailVo> {
        val contentList = mutableListOf<TodoTimelineVo>()
        for (index in 0..MAX_SHOW_COUNT) {
            if (index == MAX_SHOW_COUNT) {
                break
            }

            contentList.add(list[index])
        }

        return listOf(
            MyPageActiveDetailVo(
                viewType = MyPageActiveDetailViewType.MY_TODO,
                todoList = contentList
            )
        )
    }

    private fun setListUnderToDoMaxCount(list: List<TodoTimelineVo>): List<MyPageActiveDetailVo> {
        return listOf(
            MyPageActiveDetailVo(
                viewType = MyPageActiveDetailViewType.MY_TODO,
                todoList = list
            )
        )
    }

    private fun handleGetMyPostSuccess(state: PlubingBoardListVo) {
        val originList = uiState.detailList.value
        val mergedList = if (state.totalElements > MAX_SHOW_COUNT) {
            setListOverBoardMaxCount(state.content)
        } else {
            setListUnderBoardMaxCount(state.content)
        }

        updateDetailList(originList + mergedList)
    }

    private fun setListOverBoardMaxCount(list: List<PlubingBoardVo>) : List<MyPageActiveDetailVo> {
        val contentList = mutableListOf<PlubingBoardVo>()
        for (index in 0..MAX_SHOW_COUNT) {
            if (index == MAX_SHOW_COUNT) {
                break
            }

            contentList.add(list[index])
        }

        return listOf(
            MyPageActiveDetailVo(
                viewType = MyPageActiveDetailViewType.MY_POST,
                postList = contentList
            )
        )
    }

    private fun setListUnderBoardMaxCount(list: List<PlubingBoardVo>): List<MyPageActiveDetailVo> {
        return listOf(
            MyPageActiveDetailVo(
                viewType = MyPageActiveDetailViewType.MY_POST,
                postList = list
            )
        )
    }

    private fun updateDetailList(list : List<MyPageActiveDetailVo>){
        viewModelScope.launch {
            detailListStateFlow.update { list }
        }
    }

    fun onClickBoard(feedId: Int) {
        emitEventFlow(ActiveGatheringEvent.GoToDetailBoard(feedId))
    }

    fun goPlubbingMain(){
        emitEventFlow(ActiveGatheringEvent.GoToPlubbingMain(plubingId))
    }

    fun goBack(){
        emitEventFlow(ActiveGatheringEvent.GoToBack)
    }

    fun onClickTimeline(timelineId: Int) {
        emitEventFlow(ActiveGatheringEvent.GoToDetailTodo(timelineId))
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
            updateDetailList(rebasedTimelineList)
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

    private fun getReplaceItemTodoRebasedList(timelineId: Int, vo: TodoItemVo, isTop: Boolean): List<MyPageActiveDetailVo> {
        return detailListStateFlow.value.toMutableList().apply {
            val todoListPosition = indexOfFirst { it.viewType == MyPageActiveDetailViewType.MY_TODO }
            val rebasedTodoList = this[todoListPosition].todoList.toMutableList().apply {
                val timelinePosition = indexOfFirst { it.timelineId == timelineId }
                val timelineVo = get(timelinePosition)
                val rebasedTodoList = getTodoItemListRebaseItem(timelineVo.todoList,vo, isTop)
                val rebasedTimelineVo = timelineVo.copy(todoList = rebasedTodoList)
                set(timelinePosition, rebasedTimelineVo)
            }
            val changedTodoList = this[todoListPosition].copy(
                todoList = rebasedTodoList
            )
            set(todoListPosition, changedTodoList)
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
            updateDetailList(rebasedTimelineList)
            showProofDialog(timelineId, vo)
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

    private fun showProofDialog(timelineId: Int, todoItemVo: TodoItemVo) {
        val vo = ParseTodoItemVo.mapToParse(todoItemVo)
        emitEventFlow(ActiveGatheringEvent.ShowTodoProofDialog(timelineId, vo))
    }

    fun onClickProofComplete(timelineId: Int, todoId: Int, proofFile: File) {
        postUploadImage(proofFile) {
            postTodoProof(todoId, it) {
                val proofedList = getTodoListProofChanged(timelineId, todoId)
                updateDetailList(proofedList)
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

    private fun getTodoListProofChanged(timelineId: Int, todoId: Int): List<MyPageActiveDetailVo> {
        return detailListStateFlow.value.toMutableList().apply {
            val todoListPosition = indexOfFirst { it.viewType == MyPageActiveDetailViewType.MY_TODO }
            val rebasedProofList = this[todoListPosition].todoList.toMutableList().apply {
                val timelinePosition = indexOfFirst { it.timelineId == timelineId }
                val timelineVo = get(timelinePosition)
                val proofChangedList = getTodoItemListProofChanged(timelineVo.todoList, todoId)
                val proofChangedTimelineVo = timelineVo.copy(todoList = proofChangedList)
                this[timelinePosition] = proofChangedTimelineVo
            }
            val changedProofList = this[todoListPosition].copy(
                todoList = rebasedProofList
            )
            set(todoListPosition, changedProofList)
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
        emitEventFlow(ActiveGatheringEvent.ShowMenuBottomSheetDialog(vo, menuType))
    }

    fun onClickMenuItemType(item: DialogMenuItemType, todoTimelineVo: TodoTimelineVo) {
        when (item) {
            DialogMenuItemType.TODO_PLANNER -> goToTodoPlanner(todoTimelineVo.date)
            else -> Unit
        }
    }

    private fun goToTodoPlanner(date:String) {
        emitEventFlow(ActiveGatheringEvent.GoToPlannerTodo(date))
    }

}