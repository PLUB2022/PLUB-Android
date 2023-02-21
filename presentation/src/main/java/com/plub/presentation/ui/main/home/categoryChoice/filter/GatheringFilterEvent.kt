package com.plub.presentation.ui.main.home.categoryChoice.filter

import com.plub.domain.model.vo.common.SelectedHobbyVo
import com.plub.presentation.ui.Event

sealed class GatheringFilterEvent : Event {
    data class NotifySubHobby(val vo: SelectedHobbyVo) : GatheringFilterEvent()
}