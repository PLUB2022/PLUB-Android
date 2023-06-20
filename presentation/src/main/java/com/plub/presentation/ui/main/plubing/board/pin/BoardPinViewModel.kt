package com.plub.presentation.ui.main.plubing.board.pin

import androidx.lifecycle.viewModelScope
import com.plub.domain.model.enums.DialogMenuItemType
import com.plub.domain.model.enums.DialogMenuType
import com.plub.domain.model.vo.board.BoardRequestVo
import com.plub.domain.model.vo.board.PlubingBoardVo
import com.plub.domain.usecase.DeleteBoardUseCase
import com.plub.domain.usecase.GetBoardPinsUseCase
import com.plub.domain.usecase.PutBoardChangePinUseCase
import com.plub.presentation.base.BaseTestViewModel
import com.plub.presentation.parcelableVo.ParsePlubingBoardVo
import com.plub.presentation.util.PlubingInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BoardPinViewModel @Inject constructor(
    val putBoardChangePinUseCase: PutBoardChangePinUseCase,
    val deleteBoardUseCase: DeleteBoardUseCase,
    val getBoardPinsUseCase: GetBoardPinsUseCase,
) : BaseTestViewModel<BoardPinPageState>() {

    private val plubingNameStateFlow: MutableStateFlow<String> = MutableStateFlow(PlubingInfo.info.name)
    private val pinListStateFlow: MutableStateFlow<List<PlubingBoardVo>> = MutableStateFlow(emptyList())

    override val uiState: BoardPinPageState = BoardPinPageState(
        plubingNameStateFlow.asStateFlow(),
        pinListStateFlow.asStateFlow()
    )

    private var plubingId = PlubingInfo.info.plubingId

    private var scrollToPosition: Int? = null

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

    fun onGetPinList() {
        if(pinListStateFlow.value.isNotEmpty()) return
        getPinList()
    }

    private fun getPinList() {
        viewModelScope.launch {
            getBoardPinsUseCase(plubingId).collect {
                inspectUiState(it, ::updatePinList)
            }
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
        val deletedList = pinListStateFlow.value.filterNot { it.feedId == feedId }
        updatePinList(deletedList)
    }

    private fun updateEditFeedList(vo: PlubingBoardVo) {
        val newList = pinListStateFlow.value.toMutableList()
        val idx = newList.indexOfFirst { it.feedId == vo.feedId }
        newList[idx] = vo
        updatePinList(newList)
    }

    private fun updatePinList(list: List<PlubingBoardVo>) {
        viewModelScope.launch {
            pinListStateFlow.update { list }
        }
    }

    fun goToBack(){
        emitEventFlow(BoardPinEvent.GoToBack)
    }
}
