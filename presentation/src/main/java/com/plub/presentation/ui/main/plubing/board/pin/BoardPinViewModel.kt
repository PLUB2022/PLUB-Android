package com.plub.presentation.ui.main.plubing.board.pin

import androidx.lifecycle.viewModelScope
import com.plub.domain.model.enums.DialogMenuItemType
import com.plub.domain.model.enums.DialogMenuType
import com.plub.domain.model.vo.board.BoardRequestVo
import com.plub.domain.model.vo.board.PlubingBoardVo
import com.plub.domain.usecase.DeleteBoardUseCase
import com.plub.domain.usecase.GetBoardPinsUseCase
import com.plub.domain.usecase.PutBoardChangePinUseCase
import com.plub.presentation.base.BaseViewModel
import com.plub.presentation.parcelableVo.ParsePlubingBoardVo
import com.plub.presentation.util.PlubingInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BoardPinViewModel @Inject constructor(
    val putBoardChangePinUseCase: PutBoardChangePinUseCase,
    val deleteBoardUseCase: DeleteBoardUseCase,
    val getBoardPinsUseCase: GetBoardPinsUseCase,
) : BaseViewModel<BoardPinPageState>(BoardPinPageState()) {

    private var plubingId = PlubingInfo.info.plubingId

    private var scrollToPosition:Int? = null

    fun onCompleteBoardEdit(vo: ParsePlubingBoardVo) {
        val domainVo = ParsePlubingBoardVo.mapToDomain(vo)
        updateEditFeedList(domainVo)
    }

    fun onClickMenuItemType(feedId: Int, item: DialogMenuItemType) {
        when (item) {
            DialogMenuItemType.BOARD_RELEASE_CLIP -> boardReleaseClip(feedId)
            DialogMenuItemType.BOARD_DELETE -> boardDelete(feedId)
            DialogMenuItemType.BOARD_REPORT -> emitEventFlow(BoardPinEvent.GoToReportBoard(feedId))
            DialogMenuItemType.BOARD_EDIT -> emitEventFlow(BoardPinEvent.GoToEditBoard(feedId))
            else -> Unit
        }
    }

    fun onPinUpdated() {
        scrollToPosition?.let {
            emitEventFlow(BoardPinEvent.ScrollToPosition(it))
            scrollToPosition = null
        }
    }

    fun onClickBoard(feedId: Int) {
        emitEventFlow(BoardPinEvent.GoToDetailBoard(feedId))
    }

    fun onLongClickBoard(feedId: Int, isHost: Boolean, isAuthor: Boolean) {
        val menuType = when {
            isHost && isAuthor -> DialogMenuType.BOARD_CLIP_HOST_AND_AUTHOR_TYPE
            isHost -> DialogMenuType.BOARD_CLIP_HOST_TYPE
            isAuthor -> DialogMenuType.BOARD_AUTHOR_TYPE
            else -> DialogMenuType.BOARD_COMMON_TYPE
        }
        emitEventFlow(BoardPinEvent.ShowMenuBottomSheetDialog(feedId, menuType))
    }

    fun onFetchPinList() {
        viewModelScope.launch {
            fetchPinList()
        }
    }

    private suspend fun fetchPinList() {
        getBoardPinsUseCase(plubingId).collect {
            inspectUiState(it, ::onSuccessFetchClipBoardList)
        }
    }

    private fun onSuccessFetchClipBoardList(list: List<PlubingBoardVo>) {
        updateUiState { uiState ->
            uiState.copy(
                pinList = list
            )
        }
    }

    private fun boardReleaseClip(feedId: Int) {
        viewModelScope.launch {
            val request = BoardRequestVo(plubingId, feedId)
            putBoardChangePinUseCase(request).collect {
                inspectUiState(it, {
                    updateDeletedFeedList(feedId)
                })
            }
        }
    }

    private fun boardDelete(feedId: Int) {
        viewModelScope.launch {
            val request = BoardRequestVo(plubingId, feedId)
            deleteBoardUseCase(request).collect {
                inspectUiState(it, {
                    updateDeletedFeedList(feedId)
                })
            }
        }
    }

    private fun updateDeletedFeedList(feedId: Int) {
        val deletedList = uiState.value.pinList.filterNot { it.feedId == feedId }
        updatePinList(deletedList)
    }

    private fun updateEditFeedList(vo: PlubingBoardVo) {
        val newList = uiState.value.pinList.toMutableList()
        val idx = newList.indexOfFirst { it.feedId == vo.feedId }
        newList[idx] = vo
        updatePinList(newList)
    }

    private fun updatePinList(list:List<PlubingBoardVo>) {
        updateUiState { uiState ->
            uiState.copy(
                pinList = list
            )
        }
    }
}
