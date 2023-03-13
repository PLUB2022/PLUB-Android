package com.plub.presentation.ui.main.archive.bottomsheet.dots

import com.plub.presentation.ui.Event

sealed class ArchiveDotsMenuBottomSheetEvent : Event{
    object GoToReport : ArchiveDotsMenuBottomSheetEvent()
    object DeleteArchive : ArchiveDotsMenuBottomSheetEvent()
    object EditArchive : ArchiveDotsMenuBottomSheetEvent()
}
