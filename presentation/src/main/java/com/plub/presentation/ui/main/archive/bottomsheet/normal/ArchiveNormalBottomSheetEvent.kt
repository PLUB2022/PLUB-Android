package com.plub.presentation.ui.main.archive.bottomsheet.normal

import com.plub.presentation.ui.Event

sealed class ArchiveNormalBottomSheetEvent : Event{
    data class GoToReport(val archiveId : Int) : ArchiveNormalBottomSheetEvent()
}
