package com.plub.presentation.ui.main.plubing.todo.planner

import com.plub.domain.model.enums.DialogMenuType
import com.plub.domain.model.vo.todo.TodoItemVo
import com.plub.presentation.parcelableVo.ParseTodoItemVo
import com.plub.presentation.ui.Event
import com.prolificinteractive.materialcalendarview.CalendarDay

sealed class TodoPlannerEvent : Event {
    object CalendarMonthNext : TodoPlannerEvent()
    object CalendarMonthPrevious : TodoPlannerEvent()
    object ClearTodoEditText : TodoPlannerEvent()
    object ShowKeyboard : TodoPlannerEvent()
    object HideKeyboard : TodoPlannerEvent()
    data class SetCalendarCurrentDate(val calendarDay: CalendarDay) : TodoPlannerEvent()
    data class ShowMenuBottomSheetDialog(val menuType: DialogMenuType, val todoVo: TodoItemVo) : TodoPlannerEvent()
    data class ShowTodoProofDialog(val parseTodoItemVo: ParseTodoItemVo) : TodoPlannerEvent()
}
