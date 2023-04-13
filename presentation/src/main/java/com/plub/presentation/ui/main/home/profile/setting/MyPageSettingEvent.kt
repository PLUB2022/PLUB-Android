package com.plub.presentation.ui.main.home.profile.setting

import android.net.Uri
import com.canhub.cropper.CropImageContractOptions
import com.plub.presentation.ui.Event

sealed class MyPageSettingEvent : Event{
    object ShowSelectImageBottomSheetDialog : MyPageSettingEvent()
    object GoToAlbum : MyPageSettingEvent()
    object ShowDialog : MyPageSettingEvent()
    object GoToBack : MyPageSettingEvent()
    data class GoToCamera(val uri: Uri) : MyPageSettingEvent()
    data class CropImageAndOptimize(val cropImageContractOptions: CropImageContractOptions): MyPageSettingEvent()
}
