package com.plub.presentation.ui.main.plubing.board

import androidx.lifecycle.viewModelScope
import com.plub.domain.model.enums.DialogMenuItemType
import com.plub.domain.model.enums.DialogMenuType
import com.plub.domain.model.enums.PlubingBoardType
import com.plub.domain.model.vo.board.FetchPlubingBoardRequestVo
import com.plub.domain.model.vo.board.PlubingBoardListVo
import com.plub.domain.model.vo.board.PlubingBoardVo
import com.plub.domain.model.vo.plub.PlubCardListVo
import com.plub.domain.model.vo.plub.PlubCardVo
import com.plub.domain.usecase.FetchPlubingBoardUseCase
import com.plub.domain.usecase.FetchPlubingPinsUseCase
import com.plub.presentation.base.BaseViewModel
import com.plub.presentation.ui.main.home.search.SearchingEvent
import com.plub.presentation.ui.main.home.search.SearchingViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlubingBoardViewModel @Inject constructor(
    val fetchPlubingBoardUseCase: FetchPlubingBoardUseCase,
    val fetchPlubingPinsUseCase: FetchPlubingPinsUseCase,
) : BaseViewModel<PlubingBoardPageState>(PlubingBoardPageState()) {

    companion object {
        private const val CLIP_BOARD_SIZE = 3
        private const val CLIP_BOARD_POSITION = 0
        private const val FIRST_PAGE = 1
    }

    private var isNetworkCall: Boolean = false
    private var isLastPage: Boolean = false
    private var page: Int = FIRST_PAGE

    fun onClickMenuItemType(id: Int, item: DialogMenuItemType) {
        when (item) {
            DialogMenuItemType.BOARD_FIX_CLIP -> boardFixClip(id)
            DialogMenuItemType.BOARD_REPORT -> boardReport(id)
            DialogMenuItemType.BOARD_EDIT -> boardEdit(id)
            DialogMenuItemType.BOARD_DELETE -> boardDelete(id)
            else -> Unit
        }
    }

    fun onClickClipBoard() {

    }

    fun onClickNormalBoard(id: Int) {

    }

    fun onLongClickNormalBoard(id: Int, isHost: Boolean, isAuthor: Boolean) {
        val menuType = when {
            isHost && isAuthor -> DialogMenuType.BOARD_LIST_HOST_AND_AUTHOR_TYPE
            isHost -> DialogMenuType.BOARD_LIST_HOST_TYPE
            isAuthor -> DialogMenuType.BOARD_AUTHOR_TYPE
            else -> DialogMenuType.BOARD_COMMON_TYPE
        }
        emitEventFlow(PlubingBoardEvent.ShowMenuBottomSheetDialog(id, menuType))
    }

    fun onFetchClipBoardList(plubingId: Int) {
        viewModelScope.launch {
            fetchClipBoardList(plubingId)
        }
    }

    fun onFetchBoardList(plubingId: Int) {
        viewModelScope.launch {
            isNetworkCall = true
            page = FIRST_PAGE
            fetchPlubingBoardList(plubingId)
        }
    }

    fun onScrollChanged(isBottom: Boolean, isDownScroll: Boolean, plubingId: Int) {
        if (isBottom && isDownScroll && !isLastPage && !isNetworkCall) viewModelScope.launch {
            isNetworkCall = true
            fetchPlubingBoardList(plubingId)
        }
    }

    private suspend fun fetchPlubingBoardList(plubingId: Int) {
        val requestVo = FetchPlubingBoardRequestVo(page, plubingId)
        fetchPlubingBoardUseCase(requestVo).collect {
            inspectUiState(it, ::onSuccessFetchPlubingBoardList)
        }
    }

    private fun onSuccessFetchPlubingBoardList(vo: PlubingBoardListVo) {
        vo.run {
            val mergedList = getMergeList(content)
            updateUiState { ui ->
                ui.copy(
                    boardList = mergedList
                )
            }
            isLastPage = last
            page++
            isNetworkCall = false
        }
    }

    private fun getMergeList(list: List<PlubingBoardVo>): List<PlubingBoardVo> {
        val originList = uiState.value.boardList
        return originList + list
    }

    private suspend fun fetchClipBoardList(plubingId: Int) {
        fetchPlubingPinsUseCase(plubingId).collect {
            inspectUiState(it, ::onSuccessFetchClipBoardList)
        }
    }

    private fun onSuccessFetchClipBoardList(list: List<PlubingBoardVo>) {
        val isClipBoardVisible = list.isNotEmpty()
        updateUiState { uiState ->
            uiState.copy(
                clipBoardList = list.take(CLIP_BOARD_SIZE),
                boardList = getClipBoardList(uiState.boardList, isClipBoardVisible)
            )
        }
        emitEventFlow(PlubingBoardEvent.NotifyClipBoardChanged)
    }

    private fun getClipBoardList(
        boardList: List<PlubingBoardVo>,
        isClipBoardVisible: Boolean
    ): List<PlubingBoardVo> {
        val hasClipBoard = boardList.firstOrNull()?.viewType == PlubingBoardType.CLIP_BOARD
        val clipBoardVo = PlubingBoardVo(viewType = PlubingBoardType.CLIP_BOARD)
        return boardList.toMutableList().apply {
            when {
                !hasClipBoard && isClipBoardVisible -> add(CLIP_BOARD_POSITION, clipBoardVo)
                hasClipBoard && !isClipBoardVisible -> removeAt(CLIP_BOARD_POSITION)
            }
        }
    }

    private fun boardFixClip(id: Int) {}
    private fun boardReport(id: Int) {}
    private fun boardDelete(id: Int) {}
    private fun boardEdit(id: Int) {}
}
