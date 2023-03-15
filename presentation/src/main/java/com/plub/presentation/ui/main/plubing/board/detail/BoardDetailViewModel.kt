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
import com.plub.presentation.base.BaseTestViewModel
import com.plub.presentation.util.PlubUser
import com.plub.presentation.util.PlubingInfo
import com.plub.presentation.util.ResourceProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.properties.Delegates

@HiltViewModel
class BoardDetailViewModel @Inject constructor(
    val resourceProvider: ResourceProvider,
    val getBoardDetailUseCase: GetBoardDetailUseCase,
    val putBoardChangePinUseCase: PutBoardChangePinUseCase,
    val deleteBoardUseCase: DeleteBoardUseCase,
    val getBoardCommentsUseCase: GetBoardCommentsUseCase,
    val postBoardCommentCreateUseCase: PostBoardCommentCreateUseCase,
    val deleteBoardCommentUseCase: DeleteBoardCommentUseCase,
    val putBoardCommentEditUseCase: PutBoardCommentEditUseCase
) : BaseTestViewModel<BoardDetailPageState>() {

    companion object {
        private const val SCROLL_TOP = 0
        private const val FIRST_CURSOR = 0
        private const val DETAIL_INFO_POSITION = 0
    }

    private val plubingNameStateFlow: MutableStateFlow<String> = MutableStateFlow(PlubingInfo.info.name)
    private val profileImageStateFlow: MutableStateFlow<String> = MutableStateFlow(PlubUser.info.profileImage)
    private val isEditCommentModeStateFlow: MutableStateFlow<Boolean> = MutableStateFlow(false)
    private val isReplyWritingModeStateFlow: MutableStateFlow<Boolean> = MutableStateFlow(false)
    private val replyWritingTextStateFlow: MutableStateFlow<String> = MutableStateFlow("")
    private val boardVoStateFlow: MutableStateFlow<PlubingBoardVo> = MutableStateFlow(PlubingBoardVo())
    private val commentTextStateFlow:MutableStateFlow<String> = MutableStateFlow("")
    private val commentListStateFlow: MutableStateFlow<List<BoardCommentVo>> = MutableStateFlow(emptyList())

    override val uiState: BoardDetailPageState = BoardDetailPageState(
        plubingNameStateFlow,
        profileImageStateFlow,
        isEditCommentModeStateFlow,
        isReplyWritingModeStateFlow,
        replyWritingTextStateFlow,
        boardVoStateFlow,
        commentTextStateFlow,
        commentListStateFlow
    )

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
        if (isBottom && isDownScroll && !isLastPage && !isNetworkCall) onGetNextBoardComments()
    }

    fun onGetBoardDetail() {
        if(boardVoStateFlow.value != PlubingBoardVo()) return
        getBoardDetail()
    }

    fun onGetBoardComments() {
        if(commentListStateFlow.value.isNotEmpty()) return
        isNetworkCall = true
        isLastPage = false
        cursorId = FIRST_CURSOR
        getBoardComments()
    }

    private fun sendComment(comment: String) {
        val request = CommentCreateRequestVo(plubingId, feedId, comment, replyCommentId)
        viewModelScope.launch {
            postBoardCommentCreateUseCase(request).collect {
                inspectUiState(it, ::sendCommentSuccess)
            }
        }
    }

    private fun getBoardComments() {
        val request = GetBoardCommentsRequestVo(plubingId, feedId, cursorId)
        viewModelScope.launch {
            getBoardCommentsUseCase(request).collect {
                inspectUiState(it, ::onSuccessFetchCommentList)
            }
        }
    }

    private fun getBoardDetail() {
        val request = BoardRequestVo(plubingId, feedId)
        viewModelScope.launch {
            getBoardDetailUseCase(request).collect {
                inspectUiState(it, ::onSuccessGetBoardDetail)
            }
        }
    }

    private fun boardChangeClip() {
        viewModelScope.launch {
            val request = BoardRequestVo(plubingId, feedId)
            putBoardChangePinUseCase(request).collect {
                inspectUiState(it, {
                    getBoardDetail()
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

    private fun onGetNextBoardComments() {
        isNetworkCall = true
        cursorUpdate()
        getBoardComments()
    }

    private fun onSuccessFetchCommentList(vo: BoardCommentListVo) {
        updateCommentList(getMergeList(vo.content))
        isLastPage = vo.last
        isNetworkCall = false
    }

    private fun getMergeList(list: List<BoardCommentVo>): List<BoardCommentVo> {
        val originList = commentListStateFlow.value
        return if (cursorId == FIRST_CURSOR) detailInfoAddedList(list) else originList + list
    }

    private fun sendCommentSuccess(vo: BoardCommentVo) {
        replyInputModeCancel()
        commentClear()
        updateAddCommentList(vo)
    }

    private fun onSuccessGetBoardDetail(vo: PlubingBoardVo) {
        updateBoardVo(vo)
        emitEventFlow(BoardDetailEvent.NotifyBoardDetailInfoNotify)
    }

    private fun refresh() {
        scrollToPosition = SCROLL_TOP
        isLastPage = false
        cursorId = FIRST_CURSOR
        isNetworkCall = true
        getBoardDetail()
        getBoardComments()
    }

    private fun detailInfoAddedList(list: List<BoardCommentVo>):List<BoardCommentVo> {
        return list.toMutableList().apply {
            add(DETAIL_INFO_POSITION, BoardCommentVo())
        }
    }

    private fun sendCommentEditSuccess(vo:BoardCommentVo) {
        commentEditingInputModeCancel()
        commentClear()
        updateEditCommentList(vo)
    }

    private fun updateDeletedCommentList(commentId: Int) {
        val deletedList = commentListStateFlow.value.filterNot { it.commentId == commentId }
        updateCommentList(deletedList)
    }

    private fun replyInputMode(commentId: Int, nickname: String) {
        editCommentId = null
        replyCommentId = commentId
        emitEventFlow(BoardDetailEvent.ShowKeyboard)
        val text = resourceProvider.getString(R.string.plubing_board_detail_reply_writing, nickname)
        updateReplyWritingText(text)
        updateIsReplyWritingMode(true)
        updateIsEditCommentMode(false)
    }

    private fun replyInputModeCancel() {
        replyCommentId = null
        updateIsReplyWritingMode(false)
    }

    private fun commentEditingInputMode(commentText: String, commentId: Int) {
        replyCommentId = null
        editCommentId = commentId
        emitEventFlow(BoardDetailEvent.ShowKeyboard)
        updateCommentText(commentText)
        updateIsReplyWritingMode(false)
        updateIsEditCommentMode(true)
    }

    private fun commentEditingInputModeCancel() {
        editCommentId = null
        updateIsEditCommentMode(false)
    }

    private fun commentClear() {
        updateCommentText("")
        emitEventFlow(BoardDetailEvent.HideKeyboard)
    }

    private fun cursorUpdate() {
        cursorId = if (commentListStateFlow.value.isEmpty()) FIRST_CURSOR
        else commentListStateFlow.value.drop(DETAIL_INFO_POSITION).lastOrNull()?.commentId ?: FIRST_CURSOR
    }

    private fun updateEditCommentList(vo: BoardCommentVo) {
        val newList = commentListStateFlow.value.toMutableList()
        val idx = newList.indexOfFirst { it.commentId == vo.commentId }
        newList[idx] = vo
        scrollToPosition = idx
        updateCommentList(newList)
    }

    private fun updateAddCommentList(vo: BoardCommentVo) {
        val newList = commentListStateFlow.value.toMutableList()
        val targetIdx = if (vo.commentType == PlubingCommentType.REPLY) {
            newList.indexOfLast { it.groupId == vo.groupId }
        } else newList.lastIndex
        val idx = targetIdx + 1

        newList.add(idx, vo)
        scrollToPosition = idx
        updateCommentList(newList)
    }

    private fun updateIsEditCommentMode(isEditMode: Boolean) {
        viewModelScope.launch {
            isEditCommentModeStateFlow.update { isEditMode }
        }
    }
    private fun updateIsReplyWritingMode(isReplyWriteMode: Boolean) {
        viewModelScope.launch {
            isReplyWritingModeStateFlow.update { isReplyWriteMode }
        }
    }
    private fun updateReplyWritingText(text: String) {
        viewModelScope.launch {
            replyWritingTextStateFlow.update { text }
        }
    }

    private fun updateBoardVo(vo: PlubingBoardVo) {
        viewModelScope.launch {
            boardVoStateFlow.update { vo }
        }
    }

    private fun updateCommentText(text: String) {
        viewModelScope.launch {
            commentTextStateFlow.update { text }
        }
    }

    private fun updateCommentList(list: List<BoardCommentVo>) {
        viewModelScope.launch {
            commentListStateFlow.update { list }
        }
    }
}