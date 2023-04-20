package com.plub.presentation.ui.main.profile.edit

import android.net.Uri
import com.canhub.cropper.CropImageContractOptions
import com.plub.presentation.ui.Event

sealed class MyPageEditEvent : Event{
    object ShowSelectImageBottomSheetDialog : MyPageEditEvent()
    object GoToAlbum : MyPageEditEvent()
    object ShowDialog : MyPageEditEvent()
    object GoToBack : MyPageEditEvent()
    data class GoToCamera(val uri: Uri) : MyPageEditEvent()
    data class CropImageAndOptimize(val cropImageContractOptions: CropImageContractOptions): MyPageEditEvent()
}
