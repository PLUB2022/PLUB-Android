package com.plub.presentation.ui.main.archive.bottomsheet.host

import androidx.lifecycle.viewModelScope
import com.plub.domain.model.vo.archive.ArchiveIdResponseVo
import com.plub.domain.model.vo.archive.DetailArchiveRequestVo
import com.plub.domain.usecase.DeleteArchiveUseCase
import com.plub.presentation.base.BaseViewModel
import com.plub.presentation.ui.PageState
import com.plub.presentation.ui.main.archive.bottomsheet.dots.ArchiveDotsMenuBottomSheetEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ArchiveHostBottomSheetViewModel @Inject constructor(
    private val deleteArchiveUseCase: DeleteArchiveUseCase
) : BaseViewModel<PageState.Default>(PageState.Default) {

    private var plubbingId : Int = 0
    private var archiveId : Int = 1

    fun setId(pId : Int, arcId : Int){
        plubbingId = pId
        archiveId = arcId
    }

    fun deleteArchive(){
        val request = DetailArchiveRequestVo(plubbingId, archiveId)
        viewModelScope.launch {
            deleteArchiveUseCase(request).collect{state->
                inspectUiState(state, ::handleSuccessDelete)
            }
        }
    }

    private fun handleSuccessDelete(vo : ArchiveIdResponseVo){
        emitEventFlow(ArchiveDotsMenuBottomSheetEvent.DeleteArchive)
    }

    fun goToReport(){
        emitEventFlow(ArchiveDotsMenuBottomSheetEvent.GoToReport)
    }
}