package com.plub.presentation.ui.main.plubing.schedule

import com.plub.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PlubingScheduleViewModel @Inject constructor(

) : BaseViewModel<PlubingSchedulePageState>(PlubingSchedulePageState()) {

    fun onClickAddScheduleButton() {
        emitEventFlow(PlubingScheduleEvent.GoToAddSchedule(uiState.value.plubbingId))
    }
}
