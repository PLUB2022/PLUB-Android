package com.plub.presentation.ui.main.noti

import androidx.lifecycle.viewModelScope
import com.plub.domain.error.NotificationError
import com.plub.domain.model.vo.notification.NotificationResponseVo
import com.plub.domain.model.vo.notification.NotificationsResponseVo
import com.plub.domain.usecase.GetMyNotificationsUseCase
import com.plub.domain.usecase.PutReadNotificationUseCase
import com.plub.presentation.base.BaseTestViewModel
import com.plub.presentation.ui.PageState
import com.plub.presentation.util.PlubLogger
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotiViewModel @Inject constructor(
    private val getMyNotificationsUseCase: GetMyNotificationsUseCase,
    private val putReadNotificationUseCase: PutReadNotificationUseCase
): BaseTestViewModel<NotiPageState>() {

    private val notiListStateFlow: MutableStateFlow<List<NotificationResponseVo>> = MutableStateFlow(
        emptyList()
    )

    override val uiState: NotiPageState = NotiPageState(
        notiList = notiListStateFlow.asStateFlow()
    )

    fun initNoti() {
        viewModelScope.launch {
            getMyNotificationsUseCase(Unit).collect { uiState ->
                inspectUiState(uiState, ::handleInitNotiSuccess, individualErrorCallback = {_, individual ->
                    handleNotificationError(individual as NotificationError)
                })
            }
        }
    }

    private fun handleNotificationError(notificationError: NotificationError){
        when(notificationError){
            is NotificationError.NotFoundNotification -> TODO()
            else -> TODO()
        }
    }

    private fun handleInitNotiSuccess(data: NotificationsResponseVo) {
        notiListStateFlow.update {
            data.notifications
        }
    }

    fun readNotification(notificationId: Int) = viewModelScope.launch {
        putReadNotificationUseCase(notificationId).collect { uiState ->
            inspectUiState(uiState, { handleReadNotificationSuccess(notificationId) }, individualErrorCallback = {_, individual ->
                handleNotificationError(individual as NotificationError)
            })
        }
    }

    private fun handleReadNotificationSuccess(notificationId: Int) {
        notiListStateFlow.update { notifications ->
            notifications.map {
                if(it.notificationId == notificationId) it.copy(isRead = true)
                else it
            }
        }
    }
}