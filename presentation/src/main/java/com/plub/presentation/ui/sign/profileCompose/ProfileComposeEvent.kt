package com.plub.presentation.ui.sign.profileCompose

import android.net.Uri
import com.canhub.cropper.CropImageContractOptions
import com.plub.domain.model.vo.signUp.profile.ProfileComposeVo
import com.plub.presentation.ui.Event

sealed class ProfileComposeEvent : Event {
    data class MoveToNext(val vo: ProfileComposeVo) : ProfileComposeEvent()
    object ShowSelectImageBottomSheetDialog : ProfileComposeEvent()
    object GoToAlbum : ProfileComposeEvent()
    data class GoToCamera(val uri:Uri) : ProfileComposeEvent()
    data class CropImageAndOptimize(val cropImageContractOptions: CropImageContractOptions): ProfileComposeEvent()
}