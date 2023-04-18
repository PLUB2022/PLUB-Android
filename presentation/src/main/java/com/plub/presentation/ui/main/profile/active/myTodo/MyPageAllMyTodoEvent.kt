package com.plub.presentation.ui.main.profile.active.myTodo

import com.plub.domain.model.enums.DialogMenuType
import com.plub.domain.model.vo.todo.TodoTimelineVo
import com.plub.presentation.parcelableVo.ParseTodoItemVo
import com.plub.presentation.ui.Event

sealed class MyPageAllMyTodoEvent : Event{
    object GoToBack : MyPageAllMyTodoEvent()
    data class ShowMenuBottomSheetDialog(val todoTimelineVo: TodoTimelineVo, val menuType: DialogMenuType) : MyPageAllMyTodoEvent()
    data class ShowTodoProofDialog(val timelineId: Int, val parseTodoItemVo: ParseTodoItemVo) : MyPageAllMyTodoEvent()
    data class GoToPlannerTodo(val date: String) : MyPageAllMyTodoEvent()
    data class GoToDetailTodo(val timelineId: Int) : MyPageAllMyTodoEvent()
}