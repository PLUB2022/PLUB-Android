package com.plub.presentation.ui.main.archive.bottomsheet.upload

import com.plub.presentation.base.BaseViewModel
import com.plub.presentation.ui.PageState

class ArchiveBottomSheetViewModel : BaseViewModel<PageState.Default>(PageState.Default) {
    fun onClickCamera(){
        emitEventFlow(ArchiveBottomSheetEvent.GoToCamera)
    }

    fun onClickAlbum(){
        emitEventFlow(ArchiveBottomSheetEvent.GoToAlbum)
    }
}