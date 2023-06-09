package com.plub.presentation.ui.main.gathering.modify.recruit

import android.net.Uri
import com.canhub.cropper.CropImageContractOptions
import com.plub.presentation.ui.Event
import com.plub.presentation.ui.main.gathering.modify.guestQuestion.ModifyGuestQuestionEvent

sealed class ModifyRecruitEvent : Event {
    object GoToBack: ModifyRecruitEvent()
    object ShowSelectImageBottomSheetDialog: ModifyRecruitEvent()

    object GetImageFromGallery: ModifyRecruitEvent()

    data class GetImageFromCamera(val uri: Uri): ModifyRecruitEvent()

    data class CropImageAndOptimize(val cropImageContractOptions: CropImageContractOptions): ModifyRecruitEvent()

}