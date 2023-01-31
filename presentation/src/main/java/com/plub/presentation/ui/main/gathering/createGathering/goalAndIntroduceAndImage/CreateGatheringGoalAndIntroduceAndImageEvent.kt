package com.plub.presentation.ui.main.gathering.createGathering.goalAndIntroduceAndImage

import android.net.Uri
import com.plub.presentation.event.Event

sealed class CreateGatheringGoalAndIntroduceAndImageEvent : Event {
    object ShowSelectImageBottomSheetDialog: CreateGatheringGoalAndIntroduceAndImageEvent()
    object GetImageFromGallery: CreateGatheringGoalAndIntroduceAndImageEvent()
    data class GetImageFromCamera(val uri: Uri): CreateGatheringGoalAndIntroduceAndImageEvent()
}