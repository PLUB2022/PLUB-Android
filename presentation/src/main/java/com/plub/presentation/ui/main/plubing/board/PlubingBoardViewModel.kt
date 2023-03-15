package com.plub.presentation.ui.main.plubing.board

import androidx.lifecycle.viewModelScope
import com.plub.domain.model.enums.DialogMenuItemType
import com.plub.domain.model.enums.DialogMenuType
import com.plub.domain.model.enums.PlubingBoardType
import com.plub.domain.model.vo.board.BoardRequestVo
import com.plub.domain.model.vo.board.GetBoardFeedsRequestVo
import com.plub.domain.model.vo.board.PlubingBoardListVo
import com.plub.domain.model.vo.board.PlubingBoardVo
import com.plub.domain.usecase.DeleteBoardUseCase
import com.plub.domain.usecase.GetBoardFeedsUseCase
import com.plub.domain.usecase.GetBoardPinsUseCase
import com.plub.domain.usecase.PutBoardChangePinUseCase
import com.plub.presentation.base.BaseTestViewModel
import com.plub.presentation.parcelableVo.ParsePlubingBoardVo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.properties.Delegates

@HiltViewModel
class PlubingBoardViewModel @Inject constructor(
    val putBoardChangePinUseCase: PutBoardChangePinUseCase,
    val deleteBoardUseCase: DeleteBoardUseCase,
    val getBoardFeedsUseCase: GetBoardFeedsUseCase,
    val getBoardPinsUseCase: GetBoardPinsUseCase,
) : BaseTestViewModel<PlubingBoardPageState>() {

    companion object {
        private const val CLIP_BOARD_SIZE = 3
        private const val CLIP_BOARD_POSITION = 0
        private const val FIRST_CURSOR = 0
        private const val SCROLL_TOP = 0
    }

    private val clipBoardListStateFlow: MutableStateFlow<List<PlubingBoardVo>> = MutableStateFlow(emptyList())
    private val boardListStateFlow: MutableStateFlow<List<PlubingBoardVo>> = MutableStateFlow(emptyList())

    override val uiState: PlubingBoardPageState = PlubingBoardPageState(
        clipBoardListStateFlow.asStateFlow(),
        boardListStateFlow.asStateFlow(),
    )

    private var plubingId by Delegates.notNull<Int>()
    private var isNetworkCall: Boolean = false
    private var isLastPage: Boolean = false
    private var cursorId: Int = FIRST_CURSOR
    private var scrollToPosition:Int? = null

    fun initPlubingId(plubingId: Int) {
        this.plubingId = plubingId
    }

    fun onCompleteBoardCreate() {
        refresh()
    }

    fun onCompleteBoardEdit(vo: ParsePlubingBoardVo) {
        val domainVo = ParsePlubingBoardVo.mapToDomain(vo)
        updateEditFeedList(domainVo)
    }

    fun onClickMenuItemType(feedId: Int, item: DialogMenuItemType) {
        when (item) {
            DialogMenuItemType.BOARD_FIX_CLIP -> boardFixClip(feedId)
            DialogMenuItemType.BOARD_DELETE -> boardDelete(feedId)
            DialogMenuItemType.BOARD_REPORT -> emitEventFlow(PlubingBoardEvent.GoToReportBoard(feedId))
            DialogMenuItemType.BOARD_EDIT -> emitEventFlow(PlubingBoardEvent.GoToEditBoard(feedId))
            else -> Unit
        }
    }

    fun onClickClipBoard() {
        emitEventFlow(PlubingBoardEvent.GoToPinBoard)
    }

    fun onClickBoard(feedId: Int) {
        emitEventFlow(PlubingBoardEvent.GoToDetailBoard(feedId))
    }

    fun onBoardUpdated() {
        scrollToPosition?.let {
            emitEventFlow(PlubingBoardEvent.ScrollToPosition(it))
            scrollToPosition = null
        }
    }

    fun onLongClickBoard(feedId: Int, isHost: Boolean, isAuthor: Boolean) {
        val menuType = when {
            isHost && isAuthor -> DialogMenuType.BOARD_LIST_HOST_AND_AUTHOR_TYPE
            isHost -> DialogMenuType.BOARD_LIST_HOST_TYPE
            isAuthor -> DialogMenuType.BOARD_AUTHOR_TYPE
            else -> DialogMenuType.BOARD_COMMON_TYPE
        }
        emitEventFlow(PlubingBoardEvent.ShowMenuBottomSheetDialog(feedId, menuType))
    }

    fun onGetClipBoardList() {
        if(clipBoardListStateFlow.value.isNotEmpty()) return
        getClipBoardList()
    }

    fun onGetBoardList() {
        if(boardListStateFlow.value.isNotEmpty()) return
        isNetworkCall = true
        isLastPage = false
        cursorId = FIRST_CURSOR
        getPlubingBoardList()
    }

    fun onScrollChanged(isBottom: Boolean, isDownScroll: Boolean) {
        if (isBottom && isDownScroll && !isLastPage && !isNetworkCall) onGetNextBoardList()
    }

    private fun getPlubingBoardList() {
        val requestVo = GetBoardFeedsRequestVo(cursorId, plubingId)
        viewModelScope.launch {
            getBoardFeedsUseCase(requestVo).collect {
                inspectUiState(it, ::onSuccessGetPlubingBoardList)
            }
        }
    }

    private fun getClipBoardList() {
        viewModelScope.launch {
            getBoardPinsUseCase(plubingId).collect {
                inspectUiState(it, ::onSuccessGetClipBoardList)
            }
        }
    }

    private fun boardFixClip(feedId: Int) {
        viewModelScope.launch {
            val request = BoardRequestVo(plubingId, feedId)
            putBoardChangePinUseCase(request).collect {
                inspectUiState(it, {
                    updateDeletedFeedList(feedId)
                    getClipBoardList()
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

    private fun onGetNextBoardList() {
        isNetworkCall = true
        cursorUpdate()
        getPlubingBoardList()
    }

    private fun refresh() {
        scrollToPosition = SCROLL_TOP
        isNetworkCall = true
        isLastPage = false
        cursorId = FIRST_CURSOR
        getPlubingBoardList()
    }

    private fun onSuccessGetPlubingBoardList(vo: PlubingBoardListVo) {
        vo.run {
            val mergedList = getMergeList(content)
            updateBoardList(mergedList)
            isLastPage = last
            isNetworkCall = false
        }
    }

    private fun getMergeList(list: List<PlubingBoardVo>): List<PlubingBoardVo> {
        val originList = boardListStateFlow.value
        val pinList = originList.filter { it.viewType == PlubingBoardType.CLIP_BOARD }
        return if (cursorId == FIRST_CURSOR) pinList + list else originList + list
    }

    private fun onSuccessGetClipBoardList(list: List<PlubingBoardVo>) {
        val isClipBoardEmpty = list.isNotEmpty()
        updateClipBoardList(list.take(CLIP_BOARD_SIZE))
        updateBoardList(getBoardListClipBoardAdded(isClipBoardEmpty))
        emitEventFlow(PlubingBoardEvent.NotifyClipBoardChanged)
    }

    private fun getBoardListClipBoardAdded(isClipBoardEmpty: Boolean): List<PlubingBoardVo> {
        return boardListStateFlow.value.toMutableList().apply {
            val hasClipBoard = firstOrNull()?.viewType == PlubingBoardType.CLIP_BOARD
            val clipBoardVo = PlubingBoardVo(viewType = PlubingBoardType.CLIP_BOARD)
            when {
                !hasClipBoard && isClipBoardEmpty -> add(CLIP_BOARD_POSITION, clipBoardVo)
                hasClipBoard && !isClipBoardEmpty -> removeAt(CLIP_BOARD_POSITION)
            }
        }
    }

    private fun updateDeletedFeedList(feedId: Int) {
        val deletedList = boardListStateFlow.value.filterNot { it.feedId == feedId }
        updateBoardList(deletedList)
    }

    private fun updateEditFeedList(vo: PlubingBoardVo) {
        val newList = boardListStateFlow.value.toMutableList()
        val idx = newList.indexOfFirst { it.feedId == vo.feedId }
        newList[idx] = vo
        updateBoardList(newList)
    }

    private fun cursorUpdate() {
        cursorId = if (boardListStateFlow.value.isEmpty()) FIRST_CURSOR
        else boardListStateFlow.value.drop(CLIP_BOARD_POSITION).lastOrNull()?.feedId ?: FIRST_CURSOR
    }

    private fun updateBoardList(list: List<PlubingBoardVo>) {
        viewModelScope.launch {
            boardListStateFlow.update { list }
        }
    }

    private fun updateClipBoardList(list: List<PlubingBoardVo>) {
        viewModelScope.launch {
            clipBoardListStateFlow.update { list }
        }
    }
}
