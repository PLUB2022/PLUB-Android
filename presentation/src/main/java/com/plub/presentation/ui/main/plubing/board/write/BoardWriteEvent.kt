package com.plub.presentation.ui.main.plubing.board.write

import android.net.Uri
import com.canhub.cropper.CropImageContractOptions
import com.plub.domain.model.vo.board.PlubingBoardVo
import com.plub.presentation.parcelableVo.ParsePlubingBoardVo
import com.plub.presentation.ui.Event

sealed class BoardWriteEvent : Event {
    object ShowSelectImageBottomSheetDialog : BoardWriteEvent()
    object GoToAlbum : BoardWriteEvent()
    object GoToBack : BoardWriteEvent()
    data class GoToCamera(val uri: Uri) : BoardWriteEvent()
    data class CropImageAndOptimize(val cropImageContractOptions: CropImageContractOptions) : BoardWriteEvent()
    object CompleteCreate : BoardWriteEvent()
    data class CompleteEdit(val board: ParsePlubingBoardVo) : BoardWriteEvent()
}
