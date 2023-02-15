package com.plub.presentation.ui.main.archive.bottomsheet

import com.plub.presentation.ui.Event

sealed class ArchiveDotsBottomSheetEvent : Event{
    object GoToReport : ArchiveDotsBottomSheetEvent()
    object DeleteArchive : ArchiveDotsBottomSheetEvent()
    object EditArchive : ArchiveDotsBottomSheetEvent()
}
