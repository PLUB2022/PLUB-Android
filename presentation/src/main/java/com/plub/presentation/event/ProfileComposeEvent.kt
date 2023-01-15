package com.plub.presentation.event

import android.net.Uri
import com.plub.domain.model.vo.signUp.profile.ProfileComposeVo

sealed class ProfileComposeEvent : Event {
    data class MoveToNext(val vo: ProfileComposeVo) : ProfileComposeEvent()
    object ShowSelectImageBottomSheetDialog : ProfileComposeEvent()
    object GoToAlbum : ProfileComposeEvent()
    data class GoToCamera(val uri:Uri) : ProfileComposeEvent()
}