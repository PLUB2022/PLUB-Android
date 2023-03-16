package com.plub.presentation.ui.main.archive.bottomsheet.dots

import androidx.lifecycle.viewModelScope
import com.plub.domain.model.enums.ArchiveAccessType
import com.plub.domain.model.enums.ArchiveMenuType
import com.plub.domain.model.vo.archive.ArchiveContentResponseVo
import com.plub.domain.model.vo.archive.ArchiveIdResponseVo
import com.plub.domain.model.vo.archive.DetailArchiveRequestVo
import com.plub.domain.usecase.DeleteArchiveUseCase
import com.plub.presentation.base.BaseViewModel
import com.plub.presentation.ui.PageState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ArchiveDotsMenuBottomSheetViewModel @Inject constructor(
    private val deleteArchiveUseCase: DeleteArchiveUseCase
) : BaseViewModel<ArchiveDotsMenuBottomSheetState>(ArchiveDotsMenuBottomSheetState()) {

    private var plubbingId: Int = 0
    private var archiveId: Int = 0

    fun setId(pId: Int, arcId: Int) {
        plubbingId = pId
        archiveId = arcId
    }

    fun setMenu(archiveAccessType: ArchiveAccessType) {
        when (archiveAccessType) {
            ArchiveAccessType.HOST -> {
                updateHostStateUpdate()
            }
            ArchiveAccessType.NORMAL -> {
                updateNormalStateUpdate()
            }
            ArchiveAccessType.AUTHOR -> {
                updateAuthorStateUpdate()
            }
        }
    }

    fun onClickEvent(archiveMenuType: ArchiveMenuType) {
        when (archiveMenuType) {
            ArchiveMenuType.EDIT -> emitEventFlow(ArchiveDotsMenuBottomSheetEvent.EditArchive)
            ArchiveMenuType.REPORT -> emitEventFlow(ArchiveDotsMenuBottomSheetEvent.GoToReport)
            ArchiveMenuType.DELETE -> deleteArchive()
        }
    }

    private fun updateHostStateUpdate() {
        updateUiState { uiState ->
            uiState.copy(
                typeList = arrayListOf(
                    ArchiveMenuType.REPORT,
                    ArchiveMenuType.DELETE
                )
            )
        }
    }

    private fun updateNormalStateUpdate() {
        updateUiState { uiState ->
            uiState.copy(
                typeList = arrayListOf(
                    ArchiveMenuType.REPORT,
                )
            )
        }
    }

    private fun updateAuthorStateUpdate() {
        updateUiState { uiState ->
            uiState.copy(
                typeList = arrayListOf(
                    ArchiveMenuType.EDIT,
                    ArchiveMenuType.DELETE
                )
            )
        }
    }

    private fun deleteArchive() {
        val request = DetailArchiveRequestVo(plubbingId, archiveId)
        viewModelScope.launch {
            deleteArchiveUseCase(request).collect { state ->
                inspectUiState(state, { handleSuccessDelete() })
            }
        }
    }

    private fun handleSuccessDelete() {
        emitEventFlow(ArchiveDotsMenuBottomSheetEvent.DeleteArchive)
    }
}