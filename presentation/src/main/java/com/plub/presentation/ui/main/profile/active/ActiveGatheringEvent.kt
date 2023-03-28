package com.plub.presentation.ui.main.profile.active

import com.plub.domain.model.enums.DialogMenuType
import com.plub.domain.model.vo.todo.TodoTimelineVo
import com.plub.presentation.parcelableVo.ParseTodoItemVo
import com.plub.presentation.ui.Event

sealed class ActiveGatheringEvent : Event{
    object GoToWriteBoard : ActiveGatheringEvent()
    object GoToBack : ActiveGatheringEvent()
    data class GoToPlannerTodo(val date: String) : ActiveGatheringEvent()
    data class GoToDetailBoard(val feedId:Int) : ActiveGatheringEvent()
    data class GoToPlubbingMain(val plubbingId : Int) : ActiveGatheringEvent()
    data class GoToDetailTodo(val timelineId : Int) : ActiveGatheringEvent()
    data class ShowMenuBottomSheetDialog(val todoTimelineVo: TodoTimelineVo, val menuType: DialogMenuType) : ActiveGatheringEvent()
    data class ShowTodoProofDialog(val timelineId: Int, val parseTodoItemVo: ParseTodoItemVo) : ActiveGatheringEvent()
}
