package com.plub.presentation.ui.createGathering.peopleNumber

import com.plub.domain.model.state.createGathering.CreateGatheringPeopleNumberPageState
import com.plub.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CreateGatheringPeopleNumberViewModel @Inject constructor() :
    BaseViewModel<CreateGatheringPeopleNumberPageState>(CreateGatheringPeopleNumberPageState()) {

    val updateSeekbarProgressAndPositionX: (progress: Int, position: Float) -> Unit  = { progress, position ->
        updateSeekbarProgress(progress)
        updateSeekbarPositionX(position)
    }
    private fun updateSeekbarProgress(progress: Int) {
        updateUiState { uiState ->
            uiState.copy(
                seekBarProgress = progress
            )
        }
    }

    private fun updateSeekbarPositionX(position: Float) {
        updateUiState { uiState ->
            uiState.copy(
                seekBarPositionX = position
            )
        }
    }

}