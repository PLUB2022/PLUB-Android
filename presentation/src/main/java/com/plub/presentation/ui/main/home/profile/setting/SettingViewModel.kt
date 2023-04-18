package com.plub.presentation.ui.main.home.profile.setting

import androidx.lifecycle.viewModelScope
import com.plub.domain.usecase.PutChangePushNotificationUseCase
import com.plub.presentation.base.BaseTestViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(
    private val putChangePushNotificationUseCase: PutChangePushNotificationUseCase
) : BaseTestViewModel<SettingState>() {

    private val isSwitchCheckedStateFlow : MutableStateFlow<Boolean> = MutableStateFlow(false)

    override val uiState: SettingState = SettingState(
        isSwitchChecked = isSwitchCheckedStateFlow.asStateFlow()
    )

    fun goBack(){
        emitEventFlow(SettingEvent.GoToBack)
    }

    fun onClickNotice(){
        emitEventFlow(SettingEvent.GoToNotice)
    }

    fun onClickEmail(){
        emitEventFlow(SettingEvent.GoToEmail)
    }

    fun onClickFAQ(){
        emitEventFlow(SettingEvent.GoToFAQ)
    }

    fun changedSwitchNotify(){
        viewModelScope.launch{
            putChangePushNotificationUseCase.invoke(!uiState.isSwitchChecked.value).collect{
                inspectUiState(it, {handleSuccessChangeNotify()})
            }
        }
    }

    private fun handleSuccessChangeNotify(){
        viewModelScope.launch{
            isSwitchCheckedStateFlow.update { !uiState.isSwitchChecked.value }
        }
    }
}