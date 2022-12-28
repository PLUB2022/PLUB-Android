package com.plub.presentation.ui.createGathering.dayAndOnOfflineAndLocation

import com.plub.domain.model.state.CreateGatheringDayAndOnOfflineAndLocationPageState
import com.plub.presentation.base.BaseViewModel
import com.plub.presentation.util.PlubLogger
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CreateGatheringDayAndOnOfflineAndLocationViewModel @Inject constructor() :
    BaseViewModel<CreateGatheringDayAndOnOfflineAndLocationPageState>(
        CreateGatheringDayAndOnOfflineAndLocationPageState()
    ) {
    fun test() {
        PlubLogger.logD("test")
    }
}