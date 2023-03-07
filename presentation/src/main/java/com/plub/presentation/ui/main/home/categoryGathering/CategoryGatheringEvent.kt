package com.plub.presentation.ui.main.home.categoryGathering

import com.plub.domain.model.enums.DialogMenuItemType
import com.plub.presentation.ui.Event

sealed class CategoryGatheringEvent : Event {
    object GoToBack : CategoryGatheringEvent()
    object GoToSearch : CategoryGatheringEvent()
    object GoToCreate : CategoryGatheringEvent()
    object GoToFilter : CategoryGatheringEvent()
    data class GoToRecruit(val id : Int) : CategoryGatheringEvent()
    data class GoToHostRecruit(val id : Int) : CategoryGatheringEvent()
    object ScrollTop : CategoryGatheringEvent()
    data class ShowSelectSortTypeBottomSheetDialog(val selectedItem: DialogMenuItemType) : CategoryGatheringEvent()
}