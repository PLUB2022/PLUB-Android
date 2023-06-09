package com.plub.presentation.ui.main.gathering.modify.info

import android.net.Uri
import com.canhub.cropper.CropImageContractOptions
import com.plub.presentation.ui.Event
import com.plub.presentation.ui.main.gathering.modify.guestQuestion.ModifyGuestQuestionEvent

sealed class ModifyInfoEvent : Event {
    object GoToBack: ModifyInfoEvent()

}