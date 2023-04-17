package com.plub.presentation.ui.main.plubing.notice

import androidx.lifecycle.viewModelScope
import com.plub.domain.model.enums.DialogMenuItemType
import com.plub.domain.model.enums.DialogMenuType
import com.plub.domain.model.enums.NoticeType
import com.plub.domain.model.enums.WriteType
import com.plub.domain.model.vo.notice.GetNoticeListRequestVo
import com.plub.domain.model.vo.notice.NoticeListVo
import com.plub.domain.model.vo.notice.NoticeRequestVo
import com.plub.domain.model.vo.notice.NoticeVo
import com.plub.domain.usecase.DeleteNoticeUseCase
import com.plub.domain.usecase.GetNoticeListUseCase
import com.plub.presentation.base.BaseTestViewModel
import com.plub.presentation.parcelableVo.ParseNoticeVo
import com.plub.presentation.util.PlubingInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoticeViewModel @Inject constructor(
    val getNoticeListUseCase: GetNoticeListUseCase,
    val deleteNoticeUseCase: DeleteNoticeUseCase,
) : BaseTestViewModel<NoticePageState>() {

    private val noticeListStateFlow: MutableStateFlow<List<NoticeVo>> = MutableStateFlow(emptyList())
    private val plubingNameStateFlow: MutableStateFlow<String> = MutableStateFlow("")

    override val uiState: NoticePageState = NoticePageState(
        noticeListStateFlow.asStateFlow(),
        plubingNameStateFlow.asStateFlow()
    )

    companion object {
        private const val FIRST_CURSOR = 0
    }

    private lateinit var noticeType: NoticeType
    private val plubingId = PlubingInfo.info.plubingId
    private var isNetworkCall: Boolean = false
    private var isLastPage: Boolean = false
    private var cursorId: Int = FIRST_CURSOR

    fun initArgs(noticeType: NoticeType) {
        this.noticeType = noticeType
    }

    fun initTitle() {
        val name = if(noticeType == NoticeType.PLUBING) PlubingInfo.info.name else ""
        updatePlubingName(name)
    }

    fun onGetNotice() {
        if(noticeListStateFlow.value.isNotEmpty()) return
        isNetworkCall = true
        isLastPage = false
        cursorId = FIRST_CURSOR
        getNoticeList()
    }

    fun onScrollChanged(isBottom: Boolean, isDownScroll: Boolean) {
        if (isBottom && isDownScroll && !isLastPage && !isNetworkCall) onGetNextNoticeList()
    }

    fun onLongClickNotice(noticeVo: NoticeVo) {
        if(noticeType != NoticeType.PLUBING) return

        val menuType = noticeVo.run {
            when {
                isHost -> DialogMenuType.PLUBING_NOTICE_HOST_TYPE
                else -> DialogMenuType.PLUBING_NOTICE_COMMON_TYPE
            }
        }

        emitEventFlow(NoticeEvent.ShowMenuBottomSheetDialog(menuType, noticeVo))
    }

    fun onClickNoticeCreate() {
        emitEventFlow(NoticeEvent.GoToWriteNotice(WriteType.CREATE))
    }

    fun onClickNotice(noticeId: Int) {
        emitEventFlow(NoticeEvent.GoToDetailNotice(noticeType, noticeId))
    }

    fun onClickMenuItemType(item: DialogMenuItemType, noticeVo: NoticeVo) {
        when (item) {
            DialogMenuItemType.NOTICE_REPORT -> Unit
            DialogMenuItemType.NOTICE_DELETE -> noticeDelete(noticeVo.noticeId)
            DialogMenuItemType.NOTICE_EDIT -> emitEventFlow(NoticeEvent.GoToWriteNotice(WriteType.EDIT, ParseNoticeVo.mapToParse(noticeVo)))
            else -> Unit
        }
    }

    fun onCompleteNoticeCreate() {
        refresh()
    }

    fun onCompleteNoticeEdit(vo: ParseNoticeVo) {
        val domainVo = ParseNoticeVo.mapToDomain(vo)
        val replacedList = getReplacedEditNoticeList(domainVo)
        updateNoticeList(replacedList)
    }


    private fun noticeDelete(noticeId: Int) {
        viewModelScope.launch {
            val requestVo = NoticeRequestVo(noticeType = noticeType, plubbingId = plubingId, noticeId = noticeId)
            deleteNoticeUseCase(requestVo).collect {
                inspectUiState(it, {
                    val deletedList = noticeListStateFlow.value.filterNot { it.noticeId == noticeId }
                    updateNoticeList(deletedList)
                })
            }
        }
    }

    private fun getNoticeList() {
        viewModelScope.launch {
            val requestVo = GetNoticeListRequestVo(noticeType = noticeType, plubbingId = plubingId)
            getNoticeListUseCase(requestVo).collect {
                inspectUiState(it, ::successGetNoticeList)
            }
        }
    }

    private fun successGetNoticeList(vo: NoticeListVo) {
        isLastPage = vo.last
        val mergedList = getMergeList(vo.content)
        updateNoticeList(mergedList)
        isNetworkCall = false
    }

    private fun getMergeList(list: List<NoticeVo>): List<NoticeVo> {
        val originList = noticeListStateFlow.value
        return if (cursorId == FIRST_CURSOR) list else originList + list
    }

    private fun cursorUpdate() {
        cursorId = if (noticeListStateFlow.value.isEmpty()) FIRST_CURSOR
        else noticeListStateFlow.value.lastOrNull()?.noticeId ?: FIRST_CURSOR
    }

    private fun refresh() {
        isNetworkCall = true
        isLastPage = false
        cursorId = FIRST_CURSOR
        getNoticeList()
    }

    private fun getReplacedEditNoticeList(vo: NoticeVo): List<NoticeVo> {
        return noticeListStateFlow.value.toMutableList().apply {
            val idx = indexOfFirst { it.noticeId == vo.noticeId }
            set(idx, vo)
        }
    }

    private fun onGetNextNoticeList() {
        isNetworkCall = true
        cursorUpdate()
        getNoticeList()
    }

    private fun updateNoticeList(list: List<NoticeVo>) {
        viewModelScope.launch {
            noticeListStateFlow.update { list }
        }
    }

    private fun updatePlubingName(name: String) {
        viewModelScope.launch {
            plubingNameStateFlow.update { name }
        }
    }
}
