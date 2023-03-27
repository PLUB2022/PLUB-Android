package com.plub.presentation.ui.main.profile.active

import com.plub.presentation.parcelableVo.ParseTodoItemVo
import com.plub.presentation.ui.Event

sealed class ActiveGatheringEvent : Event{
    object GoToBack : ActiveGatheringEvent()
    data class GoToDetailBoard(val feedId:Int) : ActiveGatheringEvent()
    data class GoToPlubbingMain(val plubbingId : Int) : ActiveGatheringEvent()
    data class GoToDetailTodo(val timelineId : Int) : ActiveGatheringEvent()
    data class ShowTodoProofDialog(val timelineId: Int, val parseTodoItemVo: ParseTodoItemVo) : ActiveGatheringEvent()
}
