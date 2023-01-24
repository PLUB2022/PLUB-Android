package com.plub.presentation.ui.createGathering.goalAndIntroduceAndImage

import android.net.Uri
import com.plub.presentation.event.Event
import com.plub.presentation.state.PageState

sealed class CreateGatheringGoalAndIntroduceAndImageEvent : Event {
    object ShowSelectImageBottomSheetDialog: CreateGatheringGoalAndIntroduceAndImageEvent()
    object GetImageFromGallery: CreateGatheringGoalAndIntroduceAndImageEvent()
    data class GetImageFromCamera(val uri: Uri): CreateGatheringGoalAndIntroduceAndImageEvent()
}