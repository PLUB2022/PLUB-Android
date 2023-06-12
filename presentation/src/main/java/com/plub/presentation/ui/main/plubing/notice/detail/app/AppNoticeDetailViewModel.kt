package com.plub.presentation.ui.main.plubing.notice.detail.app

import androidx.lifecycle.viewModelScope
import com.plub.domain.model.enums.NoticeType
import com.plub.domain.model.vo.notice.*
import com.plub.domain.usecase.*
import com.plub.presentation.base.BaseTestViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.properties.Delegates

@HiltViewModel
class AppNoticeDetailViewModel @Inject constructor(
    val getNoticeDetailUseCase: GetNoticeDetailUseCase,
) : BaseTestViewModel<AppNoticeDetailState>() {

    private val noticeTitleStateFlow: MutableStateFlow<String> = MutableStateFlow("")
    private val noticeDateStateFlow: MutableStateFlow<String> = MutableStateFlow("")
    private val noticeContentStateFlow: MutableStateFlow<String> = MutableStateFlow("")

    override val uiState: AppNoticeDetailState = AppNoticeDetailState(
        noticeTitle = noticeTitleStateFlow.asStateFlow(),
        noticeDate = noticeDateStateFlow.asStateFlow(),
        noticeContent = noticeContentStateFlow.asStateFlow()
    )

    private var noticeId by Delegates.notNull<Int>()

    fun initArgs(noticeId: Int) {
        this.noticeId = noticeId
    }

    fun getNoticeDetail() {
        val request = NoticeRequestVo(noticeType = NoticeType.APP, noticeId = noticeId)
        viewModelScope.launch {
            getNoticeDetailUseCase(request).collect {
                inspectUiState(it, ::onSuccessGetNoticeDetail)
            }
        }
    }


    private fun onSuccessGetNoticeDetail(vo: NoticeVo) {
        viewModelScope.launch {
            noticeTitleStateFlow.update { vo.title }
            noticeDateStateFlow.update { vo.createAt }
            noticeContentStateFlow.update { vo.content }
        }
    }

    fun goToBack(){
        emitEventFlow(AppNoticeDetailEvent.GoToBack)
    }
}