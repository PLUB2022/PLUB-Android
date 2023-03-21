package com.plub.presentation.ui.main.noti

import com.plub.domain.model.vo.notification.NotificationResponseVo
import com.plub.presentation.base.BaseTestViewModel
import com.plub.presentation.ui.PageState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class NotiViewModel @Inject constructor(

): BaseTestViewModel<NotiPageState>() {

    private val notiListStateFlow: MutableStateFlow<List<NotificationResponseVo>> = MutableStateFlow(
        emptyList()
    )

    override val uiState: NotiPageState = NotiPageState(
        notiList = notiListStateFlow.asStateFlow()
    )
}