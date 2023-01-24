package com.plub.presentation.ui.createGathering.selectPlubCategory

import com.plub.domain.model.vo.common.SelectedHobbyVo
import com.plub.presentation.event.Event
import com.plub.presentation.state.PageState

sealed class CreateGatheringSelectPlubCategoryEvent : Event {
    data class NotifySubHobby(val selectedHobbyVo: SelectedHobbyVo): CreateGatheringSelectPlubCategoryEvent()
    object NotifyAllHobby: CreateGatheringSelectPlubCategoryEvent()
}