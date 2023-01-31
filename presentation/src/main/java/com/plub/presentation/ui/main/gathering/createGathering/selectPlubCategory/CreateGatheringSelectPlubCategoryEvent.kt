package com.plub.presentation.ui.main.gathering.createGathering.selectPlubCategory

import com.plub.domain.model.vo.common.SelectedHobbyVo
import com.plub.presentation.event.Event

sealed class CreateGatheringSelectPlubCategoryEvent : Event {
    data class NotifySubHobby(val selectedHobbyVo: SelectedHobbyVo): CreateGatheringSelectPlubCategoryEvent()
    object NotifyAllHobby: CreateGatheringSelectPlubCategoryEvent()
}