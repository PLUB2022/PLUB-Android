package com.plub.presentation.ui.main.plubing.todo.detail

import com.plub.domain.model.enums.DialogMenuType
import com.plub.domain.model.vo.todo.TodoItemVo
import com.plub.presentation.parcelableVo.ParseTodoItemVo
import com.plub.presentation.ui.Event

sealed class TodoDetailEvent : Event {
    data class ShowMenuBottomSheetDialog(val menuType: DialogMenuType, val todoVo: TodoItemVo) : TodoDetailEvent()
    data class ShowTodoProofDialog(val parseTodoItemVo: ParseTodoItemVo) : TodoDetailEvent()
    data class GoToPlannerTodo(val date: String) : TodoDetailEvent()
    object GoToBack : TodoDetailEvent()
}
