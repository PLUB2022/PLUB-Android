package com.plub.presentation.ui.main.profile.active

import androidx.lifecycle.viewModelScope
import com.plub.domain.model.enums.MyPageActiveDetailViewType
import com.plub.domain.model.enums.MyPageGatheringMyType
import com.plub.domain.model.vo.board.PlubingBoardListVo
import com.plub.domain.model.vo.board.PlubingBoardVo
import com.plub.domain.model.vo.myPage.MyPageActiveDetailVo
import com.plub.domain.model.vo.myPage.MyPageActiveRequestVo
import com.plub.domain.model.vo.myPage.MyPageDetailTitleVo
import com.plub.domain.model.vo.myPage.MyPageToDoWithTitleVo
import com.plub.domain.model.vo.todo.TodoTimelineListVo
import com.plub.domain.model.vo.todo.TodoTimelineVo
import com.plub.domain.usecase.GetMyPostUseCase
import com.plub.domain.usecase.GetMyToDoWithTitleUseCase
import com.plub.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ActiveGatheringViewModel @Inject constructor(
    private val getMyPostUseCase: GetMyPostUseCase,
    private val getMyToDoWithTitleUseCase: GetMyToDoWithTitleUseCase
) : BaseViewModel<ActiveGatheringPageState>(ActiveGatheringPageState()) {

    companion object {
        const val FIRST_CURSOR = 0
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

    private fun updateToDoView(todoList : TodoTimelineListVo){
        val originList = uiState.value.detailList
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
        val originList = uiState.value.detailList
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
        updateUiState { uiState ->
            uiState.copy(
                detailList = list
            )
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

}