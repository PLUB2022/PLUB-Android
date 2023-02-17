package com.plub.presentation.ui.main.gathering.modifyGathering

import com.plub.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ModifyGatheringViewModel @Inject constructor() :
    BaseViewModel<ModifyGatheringPageState>(ModifyGatheringPageState()) {

}