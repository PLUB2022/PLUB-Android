package com.plub.presentation.ui.main.gathering.createGathering.goalAndIntroduceAndImage

import android.net.Uri
import com.canhub.cropper.CropImageContractOptions
import com.plub.presentation.ui.Event

sealed class CreateGatheringGoalAndIntroduceAndImageEvent : Event {
    object ShowSelectImageBottomSheetDialog: CreateGatheringGoalAndIntroduceAndImageEvent()
    object GetImageFromGallery: CreateGatheringGoalAndIntroduceAndImageEvent()
    data class GetImageFromCamera(val uri: Uri): CreateGatheringGoalAndIntroduceAndImageEvent()

    data class CropImageAndOptimize(val cropImageContractOptions: CropImageContractOptions): CreateGatheringGoalAndIntroduceAndImageEvent()
}