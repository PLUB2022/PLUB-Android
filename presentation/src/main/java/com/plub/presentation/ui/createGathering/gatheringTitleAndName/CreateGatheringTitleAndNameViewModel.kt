package com.plub.presentation.ui.createGathering.gatheringTitleAndName

import android.text.Editable
import com.plub.domain.model.state.CreateGatheringTitleAndNamePageState
import com.plub.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

@HiltViewModel
class CreateGatheringTitleAndNameViewModel @Inject constructor() :
    BaseViewModel<CreateGatheringTitleAndNamePageState>(CreateGatheringTitleAndNamePageState()) {

    fun initUiState(savedUiState: CreateGatheringTitleAndNamePageState) {
        updateUiState { uiState -> uiState.copy(
            savedUiState.introductionTitle,
            savedUiState.gatheringName
        ) }
    }

    fun updateIntroductionTitle(text: Editable) {
        updateUiState { uiState ->
            uiState.copy(introductionTitle = text.toString())
        }
    }

    fun updateGatheringName(text: Editable) {
        updateUiState { uiState ->
            uiState.copy(gatheringName = text.toString())
        }
    }
}