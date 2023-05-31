package com.plub.presentation.ui.main.plubing.notice.detail

import androidx.lifecycle.viewModelScope
import com.plub.domain.model.enums.DialogMenuItemType
import com.plub.domain.model.enums.DialogMenuType
import com.plub.domain.model.enums.NoticeType
import com.plub.domain.model.enums.PlubingCommentType
import com.plub.domain.model.vo.board.BoardCommentListVo
import com.plub.domain.model.vo.board.BoardCommentVo
import com.plub.domain.model.vo.board.PlubingBoardVo
import com.plub.domain.model.vo.notice.GetNoticeCommentsRequestVo
import com.plub.domain.model.vo.notice.NoticeCommentCreateRequestVo
import com.plub.domain.model.vo.notice.NoticeCommentEditRequestVo
import com.plub.domain.model.vo.notice.NoticeRequestVo
import com.plub.domain.model.vo.notice.NoticeVo
import com.plub.domain.usecase.DeleteNoticeCommentUseCase
import com.plub.domain.usecase.DeleteNoticeUseCase
import com.plub.domain.usecase.GetNoticeCommentsUseCase
import com.plub.domain.usecase.GetNoticeDetailUseCase
import com.plub.domain.usecase.PostNoticeCommentCreateUseCase
import com.plub.domain.usecase.PutNoticeCommentEditUseCase
import com.plub.presentation.R
import com.plub.presentation.base.BaseTestViewModel
import com.plub.presentation.util.PlubUser
import com.plub.presentation.util.PlubingInfo
import com.plub.presentation.util.ResourceProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.properties.Delegates

@HiltViewModel
class NoticeDetailViewModel @Inject constructor(
    val resourceProvider: ResourceProvider,
    val getNoticeDetailUseCase: GetNoticeDetailUseCase,
    val deleteNoticeUseCase: DeleteNoticeUseCase,
    val getNoticeCommentsUseCase: GetNoticeCommentsUseCase,
    val postNoticeCommentCreateUseCase: PostNoticeCommentCreateUseCase,
    val deleteNoticeCommentUseCase: DeleteNoticeCommentUseCase,
    val putNoticeCommentEditUseCase: PutNoticeCommentEditUseCase
) : BaseTestViewModel<NoticeDetailPageState>() {

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
    private val noticeVoStateFlow: MutableStateFlow<NoticeVo> = MutableStateFlow(NoticeVo())
    private val commentTextStateFlow: MutableStateFlow<String> = MutableStateFlow("")
    private val commentListStateFlow: MutableStateFlow<List<BoardCommentVo>> = MutableStateFlow(emptyList())

    override val uiState: NoticeDetailPageState = NoticeDetailPageState(
        plubingNameStateFlow.asStateFlow(),
        profileImageStateFlow.asStateFlow(),
        isEditCommentModeStateFlow.asStateFlow(),
        isReplyWritingModeStateFlow.asStateFlow(),
        replyWritingTextStateFlow.asStateFlow(),
        noticeVoStateFlow.asStateFlow(),
        commentTextStateFlow,
        commentListStateFlow.asStateFlow()
    )

    private lateinit var noticeType: NoticeType
    private var plubingId = PlubingInfo.info.plubingId
    private var noticeId by Delegates.notNull<Int>()

    private var cursorId: Int = FIRST_CURSOR
    private var isLastPage: Boolean = false
    private var isNetworkCall: Boolean = false

    private var scrollToPosition:Int? = null
    private var replyCommentId: Int? = null
    private var editCommentId: Int? = null

    fun initArgs(noticeId: Int, noticeType: NoticeType) {
        this.noticeId = noticeId
        this.noticeType = noticeType
    }

    fun onCompleteNoticeEdit() {
        refresh()
    }

    fun onNoticeUpdated() {
        scrollToPosition?.let {
            emitEventFlow(NoticeDetailEvent.ScrollToPosition(it))
            scrollToPosition = null
        }
    }

    fun onClickMenuItemType(item: DialogMenuItemType, commentVo: BoardCommentVo) {
        val commentId = commentVo.commentId
        when (item) {
            DialogMenuItemType.NOTICE_REPORT -> emitEventFlow(NoticeDetailEvent.GoToReportNotice(plubingId, noticeId))
            DialogMenuItemType.NOTICE_EDIT -> emitEventFlow(NoticeDetailEvent.GoToEditNotice(plubingId, noticeId))
            DialogMenuItemType.NOTICE_DELETE -> noticeDelete()
            DialogMenuItemType.BOARD_COMMENT_REPORT -> emitEventFlow(NoticeDetailEvent.GoToReportComment(plubingId, noticeId, commentId))
            DialogMenuItemType.BOARD_COMMENT_DELETE -> commentDelete(commentId)
            DialogMenuItemType.BOARD_COMMENT_EDIT -> commentEditingInputMode(commentVo.content,commentId)
            else -> Unit
        }
    }

    fun onClickNoticeMenu(vo: NoticeVo) {
        if(noticeType != NoticeType.PLUBING) return

        val menuType = vo.run {
            when {
                isHost -> DialogMenuType.PLUBING_NOTICE_HOST_TYPE
                else -> DialogMenuType.PLUBING_NOTICE_COMMON_TYPE
            }
        }
        emitEventFlow(NoticeDetailEvent.ShowMenuBottomSheetDialog(menuType))
    }

    fun onClickCommentMenu(vo: BoardCommentVo) {
        val menuType = vo.run {
            when {
                isCommentAuthor -> DialogMenuType.NOTICE_COMMENT_AUTHOR_TYPE
                isFeedAuthor -> DialogMenuType.NOTICE_COMMENT_NOTICE_AUTHOR_TYPE
                else -> DialogMenuType.NOTICE_COMMENT_COMMON_TYPE
            }
        }
        emitEventFlow(NoticeDetailEvent.ShowMenuBottomSheetDialog(menuType, vo))
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

    fun onScrollChanged() {
        if (!isLastPage && !isNetworkCall) onGetNextNoticeComments()
    }

    fun onGetNoticeDetail() {
        if(noticeVoStateFlow.value != NoticeVo()) return
        getNoticeDetail()
    }

    fun onGetNoticeComments() {
        if(commentListStateFlow.value.isNotEmpty()) return
        isNetworkCall = true
        isLastPage = false
        cursorId = FIRST_CURSOR
        getNoticeComments()
    }

    private fun sendComment(comment: String) {
        val request = NoticeCommentCreateRequestVo(plubingId, noticeId, comment, replyCommentId)
        viewModelScope.launch {
            postNoticeCommentCreateUseCase(request).collect {
                inspectUiState(it, ::sendCommentSuccess)
            }
        }
    }

    private fun getNoticeComments() {
        val request = GetNoticeCommentsRequestVo(plubingId, noticeId, cursorId)
        viewModelScope.launch {
            getNoticeCommentsUseCase(request).collect {
                inspectUiState(it, ::onSuccessFetchCommentList)
            }
        }
    }

    private fun getNoticeDetail() {
        val request = NoticeRequestVo(noticeType, plubingId, noticeId)
        viewModelScope.launch {
            getNoticeDetailUseCase(request).collect {
                inspectUiState(it, ::onSuccessGetNoticeDetail)
            }
        }
    }

    private fun noticeDelete() {
        val request = NoticeRequestVo(noticeType, plubingId, noticeId)
        viewModelScope.launch {
            deleteNoticeUseCase(request).collect {
                inspectUiState(it, {
                    emitEventFlow(NoticeDetailEvent.Finish)
                })
            }
        }
    }

    private fun commentDelete(commentId:Int) {
        val request = NoticeRequestVo(noticeType, plubingId, noticeId, commentId)
        viewModelScope.launch {
            deleteNoticeCommentUseCase(request).collect {
                inspectUiState(it, {
                    updateDeletedCommentList(commentId)
                })
            }
        }
    }

    private fun commentEdit(commentId:Int, comment: String) {
        val request = NoticeCommentEditRequestVo(plubingId, noticeId, commentId, comment)
        viewModelScope.launch {
            putNoticeCommentEditUseCase(request).collect {
                inspectUiState(it, ::sendCommentEditSuccess)
            }
        }
    }

    private fun onGetNextNoticeComments() {
        isNetworkCall = true
        cursorUpdate()
        getNoticeComments()
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

    private fun onSuccessGetNoticeDetail(vo: NoticeVo) {
        updateNoticeVo(vo)
        emitEventFlow(NoticeDetailEvent.NotifyBoardDetailInfoNotify)
    }

    private fun refresh() {
        scrollToPosition = SCROLL_TOP
        isLastPage = false
        cursorId = FIRST_CURSOR
        isNetworkCall = true
        getNoticeDetail()
        getNoticeComments()
    }

    private fun detailInfoAddedList(list: List<BoardCommentVo>):List<BoardCommentVo> {
        return list.toMutableList().apply {
            add(DETAIL_INFO_POSITION, BoardCommentVo())
        }
    }

    private fun sendCommentEditSuccess(vo: BoardCommentVo) {
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
        emitEventFlow(NoticeDetailEvent.ShowKeyboard)
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
        emitEventFlow(NoticeDetailEvent.ShowKeyboard)
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
        emitEventFlow(NoticeDetailEvent.HideKeyboard)
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

    private fun updateNoticeVo(vo: NoticeVo) {
        viewModelScope.launch {
            noticeVoStateFlow.update { vo }
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

    fun goToBack(){
        emitEventFlow(NoticeDetailEvent.GoToBack)
    }
}