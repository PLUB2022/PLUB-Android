package com.plub.presentation.ui.main.gathering.create.gatheringTitleAndName

import android.text.Editable
import com.plub.presentation.base.BaseViewModel
import com.plub.presentation.ui.PageState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CreateGatheringTitleAndNameViewModel @Inject constructor() :
    BaseViewModel<CreateGatheringTitleAndNamePageState>(CreateGatheringTitleAndNamePageState()) {

    fun initUiState(savedUiState: PageState) {
        if (uiState.value != CreateGatheringTitleAndNamePageState())
            return
        if (savedUiState is CreateGatheringTitleAndNamePageState) {
            updateUiState { uiState ->
                uiState.copy(
                    introductionTitle = savedUiState.introductionTitle,
                    gatheringName = savedUiState.gatheringName
                )
            }
        }
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