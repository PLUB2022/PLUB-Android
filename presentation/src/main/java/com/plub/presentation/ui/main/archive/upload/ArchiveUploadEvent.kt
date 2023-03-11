package com.plub.presentation.ui.main.archive.upload

import com.plub.presentation.ui.Event

sealed class ArchiveUploadEvent : Event{
    object GoToBack : ArchiveUploadEvent()
    object ShowBottomSheet : ArchiveUploadEvent()
}
