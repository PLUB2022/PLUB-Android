package com.plub.presentation.ui.main.archive.bottomsheet.upload

import com.plub.presentation.ui.Event

sealed class ArchiveBottomSheetEvent : Event{
    object GoToCamera : ArchiveBottomSheetEvent()
    object GoToAlbum : ArchiveBottomSheetEvent()
}
