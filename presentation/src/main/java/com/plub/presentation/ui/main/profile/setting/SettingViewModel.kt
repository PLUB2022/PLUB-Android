package com.plub.presentation.ui.main.profile.setting

import androidx.lifecycle.viewModelScope
import com.plub.domain.error.AccountError
import com.plub.domain.usecase.GetLogoutUseCase
import com.plub.domain.usecase.PostRevokeUseCase
import com.plub.domain.usecase.PutChangePushNotificationUseCase
import com.plub.domain.usecase.PutInactiveUseCase
import com.plub.presentation.base.BaseTestViewModel
import com.plub.presentation.util.PlubUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(
    private val putChangePushNotificationUseCase: PutChangePushNotificationUseCase,
    private val getLogoutUseCase: GetLogoutUseCase,
    private val putInactiveUseCase: PutInactiveUseCase,
    private val postRevokeUseCase: PostRevokeUseCase
) : BaseTestViewModel<SettingState>() {

    private val isSwitchCheckedStateFlow : MutableStateFlow<Boolean> = MutableStateFlow(PlubUser.info.isReceivedPushNotification)

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

    fun showLogoutDialog(){
        emitEventFlow(SettingEvent.ShowLogoutDialog)
    }

    fun showInactivationDialog(){
        emitEventFlow(SettingEvent.ShowInactivationDialog)
    }

    fun showRevokeDialog(){
        emitEventFlow(SettingEvent.ShowRevokeDialog)
    }

    fun onClickLogout(){
        viewModelScope.launch {
            getLogoutUseCase(Unit).collect{
                inspectUiState(it, {goToLogin()})
            }
        }
    }

    private fun goToLogin(){
        emitEventFlow(SettingEvent.GoToLogin)
    }

    fun changedSwitchNotify(){
        viewModelScope.launch{
            putChangePushNotificationUseCase(uiState.isSwitchChecked.value).collect{
                inspectUiState(it, {handleSuccessChangeNotify()})
            }
        }
    }

    private fun handleSuccessChangeNotify(){
        viewModelScope.launch{
            PlubUser.updateInfo(PlubUser.info.copy(
                isReceivedPushNotification = uiState.isSwitchChecked.value
            ))
            isSwitchCheckedStateFlow.update { uiState.isSwitchChecked.value }
        }
    }

    fun onClickInactivation(){
        viewModelScope.launch {
            putInactiveUseCase(false).collect{
                inspectUiState(it, {goToLogin()}){ _, individual ->
                    handleAccountError(individual as AccountError)
                }
            }
        }
    }

    private fun handleAccountError(accountError: AccountError) {
        when (accountError) {
            is AccountError.AlreadyInactiveAccount -> TODO()
            AccountError.Common -> TODO()
            is AccountError.DuplicatedEmail -> TODO()
            is AccountError.DuplicatedNickname -> TODO()
            is AccountError.InvalidNickname -> TODO()
            is AccountError.InvalidSocialType -> TODO()
            is AccountError.NicknameChangeLimit -> TODO()
            is AccountError.NotFoundAccount -> TODO()
            is AccountError.RoleAccess -> TODO()
            is AccountError.SelfReport -> TODO()
            is AccountError.SuspendedAccount -> TODO()
        }
    }

    fun onClickRevoke(){
        viewModelScope.launch {
            postRevokeUseCase(Unit).collect{
                inspectUiState(it, {goToLogin()})
            }
        }
    }

    fun onClickServicePolices(){
        emitEventFlow(SettingEvent.GoToServicePolicesPage)
    }

    fun onClickPersonalPolices(){
        emitEventFlow(SettingEvent.GoToPersonalPolicesPage)
    }
}