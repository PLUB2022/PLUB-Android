package com.plub.presentation.ui.main.plubing.schedule.add

import android.text.Editable
import com.plub.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PlubingAddScheduleViewModel @Inject constructor(

) : BaseViewModel<PlubingAddSchedulePageState>(PlubingAddSchedulePageState()) {

    fun updateScheduleTitle(text: Editable) {
        updateUiState { uiState ->
            uiState.copy(scheduleTitle = text.toString())
        }
    }

    fun updateMemo(text: Editable) {
        updateUiState { uiState ->
            uiState.copy(memo = text.toString())
        }
    }

    fun onClickIsAllDay() {
        updateUiState { uiState ->
            uiState.copy(isAllDay = uiState.isAllDay.not())
        }
    }
}
