package com.plub.presentation.ui.main.archive

import android.net.Uri
import com.canhub.cropper.CropImageContractOptions
import com.plub.domain.model.enums.ArchiveAccessType
import com.plub.domain.model.vo.archive.ArchiveDetailResponseVo
import com.plub.presentation.ui.Event

sealed class ArchiveEvent : Event{
    object GoToBack : ArchiveEvent()
    object ClickUploadBottomSheet : ArchiveEvent()
    object GoToAlbum : ArchiveEvent()
    object FailUpload : ArchiveEvent()
    data class SeeDotsHostBottomSheet(val archiveId: Int) : ArchiveEvent()
    data class SeeDotsAuthorBottomSheet(val archiveId: Int)  : ArchiveEvent()
    data class SeeDotsNormalBottomSheet(val archiveId: Int)  : ArchiveEvent()
    data class GoToEdit(val archiveId: Int) : ArchiveEvent()
    data class GoToReport(val archiveId : Int) : ArchiveEvent()
    data class GoToArchiveUpload(val fileUri : String, val title : String) : ArchiveEvent()
    data class SeeDetailArchiveDialog(val responseVo: ArchiveDetailResponseVo) : ArchiveEvent()
    data class GoToCamera(val uri: Uri) : ArchiveEvent()
    data class CropImageAndOptimize(val cropImageContractOptions: CropImageContractOptions): ArchiveEvent()
}
