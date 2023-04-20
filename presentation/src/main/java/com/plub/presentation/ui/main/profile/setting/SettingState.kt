package com.plub.presentation.ui.main.profile.setting

import com.plub.presentation.ui.PageState
import kotlinx.coroutines.flow.StateFlow

data class SettingState(
    val isSwitchChecked : StateFlow<Boolean>
): PageState
