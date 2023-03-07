package com.plub.presentation.ui.main.plubing.board.detail

import androidx.lifecycle.viewModelScope
import com.plub.domain.model.enums.DialogMenuItemType
import com.plub.domain.model.enums.DialogMenuType
import com.plub.domain.model.enums.PlubingCommentType
import com.plub.domain.model.vo.board.BoardCommentListVo
import com.plub.domain.model.vo.board.BoardCommentVo
import com.plub.domain.model.vo.board.BoardRequestVo
import com.plub.domain.model.vo.board.CommentCreateRequestVo
import com.plub.domain.model.vo.board.CommentEditRequestVo
import com.plub.domain.model.vo.board.GetBoardCommentsRequestVo
import com.plub.domain.model.vo.board.PlubingBoardVo
import com.plub.domain.usecase.DeleteBoardCommentUseCase
import com.plub.domain.usecase.DeleteBoardUseCase
import com.plub.domain.usecase.GetBoardCommentsUseCase
import com.plub.domain.usecase.GetBoardDetailUseCase
import com.plub.domain.usecase.PostBoardCommentCreateUseCase
import com.plub.domain.usecase.PutBoardChangePinUseCase
import com.plub.domain.usecase.PutBoardCommentEditUseCase
import com.plub.presentation.R
import com.plub.presentation.base.BaseViewModel
import com.plub.presentation.util.ImageUtil
import com.plub.presentation.util.PlubUser
import com.plub.presentation.util.PlubingInfo
import com.plub.presentation.util.ResourceProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.properties.Delegates

@HiltViewModel
class BoardDetailViewModel @Inject constructor(
    val resourceProvider: ResourceProvider,
    val imageUtil: ImageUtil,
    val getBoardDetailUseCase: GetBoardDetailUseCase,
    val putBoardChangePinUseCase: PutBoardChangePinUseCase,
    val deleteBoardUseCase: DeleteBoardUseCase,
    val getBoardCommentsUseCase: GetBoardCommentsUseCase,
    val postBoardCommentCreateUseCase: PostBoardCommentCreateUseCase,
    val deleteBoardCommentUseCase: DeleteBoardCommentUseCase,
    val putBoardCommentEditUseCase: PutBoardCommentEditUseCase
) : BaseViewModel<BoardDetailPageState>(BoardDetailPageState()) {

    companion object {
        private const val FIRST_CURSOR = 0
        private const val DETAIL_INFO_POSITION = 0
        private const val DETAIL_INFO_SIZE = 1
    }

    private var plubingId = PlubingInfo.info.plubingId
    private var feedId by Delegates.notNull<Int>()

    private var cursorId: Int = FIRST_CURSOR
    private var isLastPage: Boolean = false
    private var isNetworkCall: Boolean = false

    private var scrollToPosition:Int? = null
    private var replyCommentId: Int? = null
    private var editCommentId: Int? = null

    fun initArgs(feedId: Int) {
        this.feedId = feedId
        updateUiState { uiState ->
            uiState.copy(
                plubingName = PlubingInfo.info.name
            )
        }
    }

    fun onCompleteBoardEdit() {
        refresh()
    }

    fun onBoardUpdated() {
        scrollToPosition?.let {
            emitEventFlow(BoardDetailEvent.ScrollToPosition(it))
            scrollToPosition = null
        }
    }

    fun onClickMenuItemType(item: DialogMenuItemType, commentVo: BoardCommentVo) {
        val commentId = commentVo.commentId
        when (item) {
            DialogMenuItemType.BOARD_REPORT -> emitEventFlow(BoardDetailEvent.GoToReportBoard(plubingId, feedId))
            DialogMenuItemType.BOARD_EDIT -> emitEventFlow(BoardDetailEvent.GoToEditBoard(plubingId, feedId))
            DialogMenuItemType.BOARD_COMMENT_REPORT -> emitEventFlow(BoardDetailEvent.GoToReportComment(plubingId, feedId, commentId))
            DialogMenuItemType.BOARD_FIX_OR_RELEASE_CLIP -> boardChangeClip()
            DialogMenuItemType.BOARD_DELETE -> boardDelete()
            DialogMenuItemType.BOARD_COMMENT_DELETE -> commentDelete(commentId)
            DialogMenuItemType.BOARD_COMMENT_EDIT -> commentEditingInputMode(commentVo.content,commentId)
            else -> Unit
        }
    }

    fun onClickBoardMenu(vo: PlubingBoardVo) {
        val menuType = vo.run {
            when {
                isHost && isAuthor -> DialogMenuType.BOARD_DETAIL_HOST_AND_AUTHOR_TYPE
                isHost -> DialogMenuType.BOARD_DETAIL_HOST_TYPE
                else -> DialogMenuType.BOARD_COMMON_TYPE
            }
        }
        emitEventFlow(BoardDetailEvent.ShowMenuBottomSheetDialog(menuType))
    }

    fun onClickCommentMenu(vo: BoardCommentVo) {
        val menuType = vo.run {
            when {
                isCommentAuthor -> DialogMenuType.BOARD_COMMENT_AUTHOR_TYPE
                isFeedAuthor -> DialogMenuType.BOARD_COMMENT_FEED_AUTHOR_TYPE
                else -> DialogMenuType.BOARD_COMMENT_COMMON_TYPE
            }
        }
        emitEventFlow(BoardDetailEvent.ShowMenuBottomSheetDialog(menuType, vo))
    }

    fun onClickCommentReply(vo: BoardCommentVo) {
        replyInputMode(vo.commentId, vo.nickname)
    }

    fun onClickCommentReplyCancel() {
        replyInputModeCancel()
    }

    fun onClickCommentEditingCancel() {
        commentEditingInputModeCancel()
    }

    fun onClickSendComment(comment: String) {
        if (comment.isBlank()) return

        val editCommentId = editCommentId
        if(editCommentId == null) sendComment(comment) else commentEdit(editCommentId, comment)
    }

    fun onScrollChanged(isBottom: Boolean, isDownScroll: Boolean) {
        if (isBottom && isDownScroll && !isLastPage && !isNetworkCall) onFetchBoardComments()
    }

    fun onFetchBoardDetail() {
        fetchBoardDetail()
    }

    fun onFetchBoardComments() {
        isNetworkCall = true
        cursorUpdate()
        fetchBoardComments()
    }

    private fun fetchBoardDetail() {
        val request = BoardRequestVo(plubingId, feedId)
        viewModelScope.launch {
            getBoardDetailUseCase(request).collect {
                inspectUiState(it, ::onSuccessFetchBoardDetail)
            }
        }
    }

    private fun onSuccessFetchBoardDetail(vo: PlubingBoardVo) {
        updateUiState { uiState ->
            uiState.copy(
                boardVo = vo,
                profileImage = PlubUser.info.profileImage,
            )
        }

        emitEventFlow(BoardDetailEvent.NotifyBoardDetailInfoNotify)
    }

    private fun fetchBoardComments() {
        val request = GetBoardCommentsRequestVo(plubingId, feedId, cursorId)
        viewModelScope.launch {
            getBoardCommentsUseCase(request).collect {
                inspectUiState(it, ::onSuccessFetchCommentList)
            }
        }
    }

    private fun onSuccessFetchCommentList(vo: BoardCommentListVo) {
        updateCommentList(getMergeList(vo.content))
        isLastPage = vo.last
        isNetworkCall = false
    }

    private fun getMergeList(list: List<BoardCommentVo>): List<BoardCommentVo> {
        val originList = uiState.value.commentList
        return if (cursorId == FIRST_CURSOR) detailInfoAddedList(list) else originList + list
    }

    private fun sendComment(comment: String) {
        val request = CommentCreateRequestVo(plubingId, feedId, comment, replyCommentId)
        viewModelScope.launch {
            postBoardCommentCreateUseCase(request).collect {
                inspectUiState(it, ::sendCommentSuccess)
            }
        }
    }

    private fun sendCommentSuccess(vo: BoardCommentVo) {
        replyInputModeCancel()
        commentClear()
        updateAddCommentList(vo)
    }

    private fun refresh() {
        isNetworkCall = true
        isLastPage = false
        cursorId = FIRST_CURSOR
        fetchBoardComments()
        fetchBoardDetail()
    }

    private fun detailInfoAddedList(list: List<BoardCommentVo>):List<BoardCommentVo> {
        return list.toMutableList().apply {
            add(DETAIL_INFO_POSITION, BoardCommentVo())
        }
    }

    private fun getCommentPosition(id:Int):Int {
        return uiState.value.commentList.indexOfFirst {
            it.commentId == id
        } + DETAIL_INFO_SIZE
    }

    private fun boardChangeClip() {
        viewModelScope.launch {
            val request = BoardRequestVo(plubingId, feedId)
            putBoardChangePinUseCase(request).collect {
                inspectUiState(it, {
                    fetchBoardDetail()
                })
            }
        }
    }

    private fun boardDelete() {
        viewModelScope.launch {
            val request = BoardRequestVo(plubingId, feedId)
            deleteBoardUseCase(request).collect {
                inspectUiState(it, {
                    emitEventFlow(BoardDetailEvent.Finish)
                })
            }
        }
    }

    private fun commentDelete(commentId:Int) {
        viewModelScope.launch {
            val request = BoardRequestVo(plubingId, feedId, commentId)
            deleteBoardCommentUseCase(request).collect {
                inspectUiState(it, {
                    updateDeletedCommentList(commentId)
                })
            }
        }
    }

    private fun commentEdit(commentId:Int, comment: String) {
        viewModelScope.launch {
            val request = CommentEditRequestVo(plubingId, feedId, commentId, comment)
            putBoardCommentEditUseCase(request).collect {
                inspectUiState(it, ::sendCommentEditSuccess)
            }
        }
    }

    private fun sendCommentEditSuccess(vo:BoardCommentVo) {
        commentEditingInputModeCancel()
        commentClear()
        updateEditCommentList(vo)
    }

    private fun updateDeletedCommentList(commentId: Int) {
        val deletedList = uiState.value.commentList.filterNot { it.commentId == commentId }
        updateCommentList(deletedList)
    }

    private fun updateCommentList(commentList:List<BoardCommentVo>) {
        updateUiState { uiState ->
            uiState.copy(
                commentList = commentList
            )
        }
    }

    private fun replyInputMode(commentId: Int, nickname: String) {
        editCommentId = null
        replyCommentId = commentId
        emitEventFlow(BoardDetailEvent.ShowKeyboard)
        updateUiState { uiState ->
            uiState.copy(
                replyWritingText = resourceProvider.getString(
                    R.string.plubing_board_detail_reply_writing,
                    nickname
                ),
                replyWritingVisibility = true,
                editCommentVisibility = false,
            )
        }
    }

    private fun replyInputModeCancel() {
        replyCommentId = null
        updateUiState { uiState ->
            uiState.copy(
                replyWritingVisibility = false
            )
        }
    }

    private fun commentEditingInputMode(commentText: String, commentId: Int) {
        replyCommentId = null
        editCommentId = commentId
        emitEventFlow(BoardDetailEvent.ShowKeyboard)
        updateUiState { uiState ->
            uiState.copy(
                commentText = commentText,
                editCommentVisibility = true,
                replyWritingVisibility = false
            )
        }
    }

    private fun commentEditingInputModeCancel() {
        editCommentId = null
        updateUiState { uiState ->
            uiState.copy(
                editCommentVisibility = false
            )
        }
    }

    private fun commentClear() {
        updateUiState { uiState ->
            uiState.copy(
                commentText = ""
            )
        }
        emitEventFlow(BoardDetailEvent.HideKeyboard)
    }

    private fun cursorUpdate() {
        cursorId = if (uiState.value.commentList.isEmpty()) FIRST_CURSOR
        else uiState.value.commentList.drop(DETAIL_INFO_POSITION).lastOrNull()?.commentId ?: FIRST_CURSOR
    }

    private fun updateEditCommentList(vo: BoardCommentVo) {
        val newList = uiState.value.commentList.toMutableList()
        val idx = newList.indexOfFirst { it.commentId == vo.commentId }
        newList[idx] = vo
        scrollToPosition = idx
        updateCommentList(newList)
    }

    private fun updateAddCommentList(vo: BoardCommentVo) {
        val newList = uiState.value.commentList.toMutableList()
        val targetIdx = if (vo.commentType == PlubingCommentType.REPLY) {
            newList.indexOfLast { it.groupId == vo.groupId }
        } else newList.lastIndex
        val idx = targetIdx + 1

        newList.add(idx, vo)
        scrollToPosition = idx
        updateCommentList(newList)
    }
}