package com.plub.presentation.ui.main.gathering.createGathering.finish

import com.plub.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CreateGatheringFinishViewModel @Inject constructor() :
    BaseViewModel<CreateGatheringFinishPageState>(CreateGatheringFinishPageState()) {
}