package com.plub.presentation.ui.main.archive

import com.plub.domain.model.vo.archive.ArchiveDetailResponseVo
import com.plub.presentation.ui.Event

sealed class ArchiveEvent : Event{
    object GoToBack : ArchiveEvent()
    object ClickUploadBottomSheet : ArchiveEvent()
    data class GoToEdit(val title : String) : ArchiveEvent()
    data class GoToReport(val archiveId : Int) : ArchiveEvent()
    data class SeeAuthorBottomSheet(val archiveId : Int) : ArchiveEvent()
    data class SeeHostBottomSheet(val archiveId : Int) : ArchiveEvent()
    data class SeeNormalBottomSheet(val archiveId : Int) : ArchiveEvent()
    data class GoToArchiveUpload(val fileUri : String, val title : String) : ArchiveEvent()
    data class SeeDetailArchiveDialog(val responseVo: ArchiveDetailResponseVo) : ArchiveEvent()
}
