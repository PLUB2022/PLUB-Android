package com.plub.presentation.ui.createGathering.peopleNumber

import com.plub.presentation.base.BaseViewModel
import com.plub.presentation.state.PageState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CreateGatheringPeopleNumberViewModel @Inject constructor() :
    BaseViewModel<CreateGatheringPeopleNumberPageState>(CreateGatheringPeopleNumberPageState()) {

    fun initUiState(savedUiState: PageState) {
        if (uiState.value != CreateGatheringPeopleNumberPageState())
            return

        if (savedUiState is CreateGatheringPeopleNumberPageState) {
            updateUiState { uiState ->
                uiState.copy(
                    seekBarProgress = savedUiState.seekBarProgress,
                    seekBarPositionX = savedUiState.seekBarPositionX
                )
            }
        }
    }

    val updateSeekbarProgressAndPositionX: (progress: Int, position: Float) -> Unit =
        { progress, position ->
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