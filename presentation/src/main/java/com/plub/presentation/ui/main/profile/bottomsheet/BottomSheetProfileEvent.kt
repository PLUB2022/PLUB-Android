package com.plub.presentation.ui.main.profile.bottomsheet

import com.plub.domain.model.enums.DialogMenuType
import com.plub.domain.model.vo.todo.TodoTimelineVo
import com.plub.presentation.ui.Event
import com.plub.presentation.ui.main.profile.active.myTodo.MyPageAllMyTodoEvent

sealed class BottomSheetProfileEvent : Event{
    object CloseButtonClick : BottomSheetProfileEvent()
    data class ShowMenuBottomSheetDialog(val todoTimelineVo: TodoTimelineVo, val menuType: DialogMenuType) : BottomSheetProfileEvent()
}
