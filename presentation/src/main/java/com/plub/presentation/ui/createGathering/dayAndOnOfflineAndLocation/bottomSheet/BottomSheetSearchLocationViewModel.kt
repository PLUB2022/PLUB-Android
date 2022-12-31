package com.plub.presentation.ui.createGathering.dayAndOnOfflineAndLocation.bottomSheet

import com.plub.domain.model.state.PageState
import com.plub.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class BottomSheetSearchLocationViewModel @Inject constructor() :
BaseViewModel<PageState.Default>(
    PageState.Default
)
{

}