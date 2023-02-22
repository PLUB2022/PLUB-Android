package com.plub.presentation.ui.main.home.categoryGathering.filter

import com.plub.domain.model.enums.DaysType
import com.plub.domain.model.vo.common.SelectedHobbyVo
import com.plub.presentation.ui.Event

sealed class GatheringFilterEvent : Event {
    data class GoToCategoryGathering(val pageState: GatheringFilterState) : GatheringFilterEvent()
    data class ClickDay(val day : DaysType, val isClick : Boolean) : GatheringFilterEvent()
    data class NotifySubHobby(val vo: SelectedHobbyVo) : GatheringFilterEvent()
}