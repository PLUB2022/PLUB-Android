package com.plub.presentation.ui.main.archive.upload

import android.net.Uri
import com.canhub.cropper.CropImageContractOptions
import com.plub.presentation.ui.Event

sealed class ArchiveUploadEvent : Event{
    object GoToBack : ArchiveUploadEvent()
    object GoToAlbum : ArchiveUploadEvent()
    object ShowBottomSheet : ArchiveUploadEvent()
    data class GoToCamera(val uri: Uri) : ArchiveUploadEvent()
    data class CropImageAndOptimize(val cropImageContractOptions: CropImageContractOptions): ArchiveUploadEvent()
}
