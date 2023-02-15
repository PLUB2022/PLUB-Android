package com.plub.presentation.ui.main.archive.bottomsheet.normal

import com.plub.presentation.base.BaseViewModel
import com.plub.presentation.ui.PageState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ArchiveNormalBottomSheetViewModel @Inject constructor(
) : BaseViewModel<PageState.Default>(PageState.Default) {

    private var archiveId : Int = 0

    fun setArchiveId(id : Int){
        archiveId = id
    }

    fun goToReport(){
        emitEventFlow(ArchiveNormalBottomSheetEvent.GoToReport(archiveId))
    }
}