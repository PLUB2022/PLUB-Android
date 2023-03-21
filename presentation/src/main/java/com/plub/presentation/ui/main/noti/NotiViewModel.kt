package com.plub.presentation.ui.main.noti

import androidx.lifecycle.viewModelScope
import com.plub.domain.model.vo.notification.NotificationResponseVo
import com.plub.domain.usecase.GetMyNotificationsUseCase
import com.plub.presentation.base.BaseTestViewModel
import com.plub.presentation.ui.PageState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotiViewModel @Inject constructor(
    private val getMyNotificationsUseCase: GetMyNotificationsUseCase
): BaseTestViewModel<NotiPageState>() {

    private val notiListStateFlow: MutableStateFlow<List<NotificationResponseVo>> = MutableStateFlow(
        emptyList()
    )

    override val uiState: NotiPageState = NotiPageState(
        notiList = notiListStateFlow.asStateFlow()
    )

    fun initNoti() {
        viewModelScope.launch {
            getMyNotificationsUseCase(Unit).collect {

            }
        }
    }
}