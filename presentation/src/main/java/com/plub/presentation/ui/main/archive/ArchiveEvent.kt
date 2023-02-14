package com.plub.presentation.ui.main.archive

import com.plub.domain.model.vo.archive.ArchiveDetailResponseVo
import com.plub.presentation.ui.Event

sealed class ArchiveEvent : Event{
    object GoToBack : ArchiveEvent()
    object ClickUploadBottomSheet : ArchiveEvent()
    data class GoToArchiveUpload(val fileUri : String) : ArchiveEvent()
    data class SeeDetailArchiveDialog(val responseVo: ArchiveDetailResponseVo) : ArchiveEvent()
}
