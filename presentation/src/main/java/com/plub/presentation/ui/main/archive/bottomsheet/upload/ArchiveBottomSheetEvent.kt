package com.plub.presentation.ui.main.archive.bottomsheet.upload

import android.net.Uri
import com.canhub.cropper.CropImageContractOptions
import com.plub.presentation.ui.Event
import java.io.File

sealed class ArchiveBottomSheetEvent : Event{
    data class GoToCamera(val uri: Uri) : ArchiveBottomSheetEvent()
    object GoToAlbum : ArchiveBottomSheetEvent()
    data class CropImageAndOptimize(val cropImageContractOptions: CropImageContractOptions): ArchiveBottomSheetEvent()
    data class EmitImageFile(val file : File?) : ArchiveBottomSheetEvent()
}
