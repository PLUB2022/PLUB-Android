package com.plub.presentation.ui.main.gathering.my

import com.plub.presentation.base.BaseTestViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MyGatheringViewModel @Inject constructor(

) : BaseTestViewModel<MyGatheringPageState>() {

    override val uiState: MyGatheringPageState = MyGatheringPageState()
}