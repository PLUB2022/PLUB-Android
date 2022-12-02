package com.plub.presentation.ui.createGathering.gatheringTitleAndName

import android.text.Editable
import com.plub.domain.model.state.CreateGatheringTitleAndNamePageState
import com.plub.domain.model.state.PageState
import com.plub.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CreateGatheringTitleAndNameViewModel @Inject constructor() :
    BaseViewModel<CreateGatheringTitleAndNamePageState>(CreateGatheringTitleAndNamePageState()) {

    fun introductionTitleChangeListener(text: Editable) {
        updateUiState { uiState ->
            uiState.copy(introductionTitle = text.toString())
        }
    }

    fun gatheringNameChangeListener(text: Editable) {
        updateUiState { uiState ->
            uiState.copy(gatheringName = text.toString())
        }
    }
}