package com.plub.presentation.ui.main.plubing.todo

import com.plub.domain.model.enums.DialogMenuType
import com.plub.domain.model.vo.todo.TodoTimelineVo
import com.plub.presentation.parcelableVo.ParseTodoItemVo
import com.plub.presentation.ui.Event

sealed class PlubingTodoEvent : Event {
    data class ShowMenuBottomSheetDialog(val todoTimelineVo: TodoTimelineVo, val menuType: DialogMenuType) : PlubingTodoEvent()
    data class ShowTodoProofDialog(val parseTodoItemVo: ParseTodoItemVo) : PlubingTodoEvent()
}
