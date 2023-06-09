package com.plub.presentation.ui.main.profile.other

import com.plub.domain.model.enums.DialogMenuType
import com.plub.domain.model.vo.todo.TodoTimelineVo
import com.plub.presentation.ui.Event

sealed class OtherProfileEvent : Event{
    object CloseButtonClick : OtherProfileEvent()
    data class GoToDoDetail(val timelineId : Int) : OtherProfileEvent()
    data class GoToProfileReport(val accountId : Int) : OtherProfileEvent()
    data class ShowMenuBottomSheetDialog(val todoTimelineVo: TodoTimelineVo, val menuType: DialogMenuType) : OtherProfileEvent()
}

