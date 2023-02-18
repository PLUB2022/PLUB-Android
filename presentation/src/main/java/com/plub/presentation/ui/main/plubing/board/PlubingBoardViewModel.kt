package com.plub.presentation.ui.main.plubing.board

import androidx.lifecycle.viewModelScope
import com.plub.domain.model.enums.DialogMenuItemType
import com.plub.domain.model.enums.DialogMenuType
import com.plub.domain.model.enums.PlubingBoardType
import com.plub.domain.model.vo.board.FetchPlubingBoardRequestVo
import com.plub.domain.model.vo.board.PlubingBoardListVo
import com.plub.domain.model.vo.board.PlubingBoardVo
import com.plub.domain.usecase.DeletePlubingBoardUseCase
import com.plub.domain.usecase.FetchPlubingBoardUseCase
import com.plub.domain.usecase.FetchPlubingPinsUseCase
import com.plub.domain.usecase.PutPlubingBoardPinChangeUseCase
import com.plub.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.properties.Delegates

@HiltViewModel
class PlubingBoardViewModel @Inject constructor(
    val putPlubingBoardPinChangeUseCase: PutPlubingBoardPinChangeUseCase,
    val deletePlubingBoardUseCase: DeletePlubingBoardUseCase,
    val fetchPlubingBoardUseCase: FetchPlubingBoardUseCase,
    val fetchPlubingPinsUseCase: FetchPlubingPinsUseCase,
) : BaseViewModel<PlubingBoardPageState>(PlubingBoardPageState()) {

    companion object {
        private const val CLIP_BOARD_SIZE = 3
        private const val CLIP_BOARD_POSITION = 0
        private const val FIRST_PAGE = 1
    }

    private var plubingId by Delegates.notNull<Int>()
    private var isNetworkCall: Boolean = false
    private var isLastPage: Boolean = false
    private var page: Int = FIRST_PAGE

    fun initPlubingId(plubingId: Int) {
        this.plubingId = plubingId
    }

    fun onClickMenuItemType(feedId: Int, item: DialogMenuItemType) {
        when (item) {
            DialogMenuItemType.BOARD_FIX_CLIP -> boardFixClip(feedId)
            DialogMenuItemType.BOARD_DELETE -> boardDelete(feedId)
            DialogMenuItemType.BOARD_REPORT -> emitEventFlow(
                PlubingBoardEvent.GoToReportBoard(
                    feedId
                )
            )

            DialogMenuItemType.BOARD_EDIT -> emitEventFlow(PlubingBoardEvent.GoToEditBoard(feedId))
            else -> Unit
        }
    }

    fun onClickClipBoard() {

    }

    fun onClickNormalBoard(feedId: Int) {

    }

    fun onLongClickNormalBoard(feedId: Int, isHost: Boolean, isAuthor: Boolean) {
        val menuType = when {
            isHost && isAuthor -> DialogMenuType.BOARD_LIST_HOST_AND_AUTHOR_TYPE
            isHost -> DialogMenuType.BOARD_LIST_HOST_TYPE
            isAuthor -> DialogMenuType.BOARD_AUTHOR_TYPE
            else -> DialogMenuType.BOARD_COMMON_TYPE
        }
        emitEventFlow(PlubingBoardEvent.ShowMenuBottomSheetDialog(feedId, menuType))
    }

    fun onFetchClipBoardList() {
        viewModelScope.launch {
            fetchClipBoardList()
        }
    }

    fun onFetchBoardList() {
        viewModelScope.launch {
            isNetworkCall = true
            page = FIRST_PAGE
            fetchPlubingBoardList()
        }
    }

    fun onScrollChanged(isBottom: Boolean, isDownScroll: Boolean) {
        if (isBottom && isDownScroll && !isLastPage && !isNetworkCall) viewModelScope.launch {
            isNetworkCall = true
            fetchPlubingBoardList()
        }
    }

    private suspend fun fetchPlubingBoardList() {
        val requestVo = FetchPlubingBoardRequestVo(page, plubingId)
        fetchPlubingBoardUseCase(requestVo).collect {
            inspectUiState(it, ::onSuccessFetchPlubingBoardList)
        }
    }

    private fun onSuccessFetchPlubingBoardList(vo: PlubingBoardListVo) {
        vo.run {
            val mergedList = getMergeList(content)
            updateBoardList(mergedList)
            isLastPage = last
            page++
            isNetworkCall = false
        }
    }

    private fun getMergeList(list: List<PlubingBoardVo>): List<PlubingBoardVo> {
        val originList = uiState.value.boardList
        return originList + list
    }

    private suspend fun fetchClipBoardList() {
        fetchPlubingPinsUseCase(plubingId).collect {
            inspectUiState(it, ::onSuccessFetchClipBoardList)
        }
    }

    private fun onSuccessFetchClipBoardList(list: List<PlubingBoardVo>) {
        val isClipBoardEmpty = list.isNotEmpty()
        updateUiState { uiState ->
            uiState.copy(
                clipBoardList = list.take(CLIP_BOARD_SIZE),
                boardList = getClipBoardList(uiState.boardList, isClipBoardEmpty)
            )
        }
        emitEventFlow(PlubingBoardEvent.NotifyClipBoardChanged)
    }

    private fun getClipBoardList(
        boardList: List<PlubingBoardVo>,
        isClipBoardEmpty: Boolean
    ): List<PlubingBoardVo> {
        val hasClipBoard = boardList.firstOrNull()?.viewType == PlubingBoardType.CLIP_BOARD
        val clipBoardVo = PlubingBoardVo(viewType = PlubingBoardType.CLIP_BOARD)
        return boardList.toMutableList().apply {
            when {
                !hasClipBoard && isClipBoardEmpty -> add(CLIP_BOARD_POSITION, clipBoardVo)
                hasClipBoard && !isClipBoardEmpty -> removeAt(CLIP_BOARD_POSITION)
            }
        }
    }

    private fun boardFixClip(feedId: Int) {
        viewModelScope.launch {
            putPlubingBoardPinChangeUseCase(feedId).collect {
                inspectUiState(it, {
                    updateDeletedFeedList(feedId)
                    onFetchClipBoardList()
                })
            }
        }
    }

    private fun boardDelete(feedId: Int) {
        viewModelScope.launch {
            deletePlubingBoardUseCase(feedId).collect {
                inspectUiState(it, {
                    updateDeletedFeedList(feedId)
                    onFetchClipBoardList()
                })
            }
        }
    }

    private fun updateDeletedFeedList(feedId: Int) {
        val deletedList = uiState.value.boardList.filterNot { it.feedId == feedId }
        updateBoardList(deletedList)
    }

    private fun updateBoardList(list:List<PlubingBoardVo>) {
        updateUiState { uiState ->
            uiState.copy(
                boardList = list
            )
        }
    }
}