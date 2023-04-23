package com.plub.presentation.ui.main.gathering.modifyGathering.recruit

import android.net.Uri
import com.canhub.cropper.CropImageContractOptions
import com.plub.presentation.ui.Event

sealed class ModifyRecruitEvent : Event {
    object ShowSelectImageBottomSheetDialog: ModifyRecruitEvent()

    object GetImageFromGallery: ModifyRecruitEvent()

    data class GetImageFromCamera(val uri: Uri): ModifyRecruitEvent()

    data class CropImageAndOptimize(val cropImageContractOptions: CropImageContractOptions): ModifyRecruitEvent()

}