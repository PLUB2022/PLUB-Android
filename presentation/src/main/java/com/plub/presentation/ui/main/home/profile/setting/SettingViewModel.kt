package com.plub.presentation.ui.main.home.profile.setting

import com.plub.presentation.base.BaseViewModel
import com.plub.presentation.ui.PageState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor() : BaseViewModel<PageState.Default>(PageState.Default) {

    fun goBack(){
        emitEventFlow(SettingEvent.GoToBack)
    }
}