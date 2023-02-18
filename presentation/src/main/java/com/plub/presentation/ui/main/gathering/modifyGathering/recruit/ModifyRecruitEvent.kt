package com.plub.presentation.ui.main.gathering.modifyGathering.recruit

import com.plub.presentation.ui.Event

sealed class ModifyRecruitEvent : Event {
    object ShowSelectImageBottomSheetDialog: ModifyRecruitEvent()
}