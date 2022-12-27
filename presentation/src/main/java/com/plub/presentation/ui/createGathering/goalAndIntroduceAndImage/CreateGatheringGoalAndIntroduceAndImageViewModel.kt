package com.plub.presentation.ui.createGathering.goalAndIntroduceAndImage

import android.app.Activity.RESULT_OK
import android.text.Editable
import androidx.activity.result.ActivityResult
import androidx.lifecycle.viewModelScope
import com.plub.domain.model.state.CreateGatheringGoalAndIntroduceAndPicturePageState
import com.plub.presentation.base.BaseViewModel
import com.plub.presentation.util.ImageUtil
import com.plub.presentation.util.PlubLogger
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@HiltViewModel
class CreateGatheringGoalAndIntroduceAndImageViewModel @Inject constructor(
    private val imageUtil: ImageUtil
) :
    BaseViewModel<CreateGatheringGoalAndIntroduceAndPicturePageState>(
        CreateGatheringGoalAndIntroduceAndPicturePageState()
    ) {
    private val _getImageFromGallery = MutableSharedFlow<Unit>(0, 1, BufferOverflow.DROP_OLDEST)
    val getImageFromGallery: SharedFlow<Unit> = _getImageFromGallery.asSharedFlow()

    fun initUiState(savedUiState: CreateGatheringGoalAndIntroduceAndPicturePageState) {
        updateUiState { uiState ->
            uiState.copy(
                gatheringGoal = savedUiState.gatheringGoal,
                gatheringIntroduce = savedUiState.gatheringIntroduce,
                gatheringImage = savedUiState.gatheringImage
            )
        }
    }

    fun updateGatheringGoal(text: Editable) {
        updateUiState { uiState ->
            uiState.copy(gatheringGoal = text.toString())
        }
    }

    fun updateGatheringIntroduce(text: Editable) {
        updateUiState { uiState ->
            uiState.copy(gatheringIntroduce = text.toString())
        }
    }

    fun onClickAddSingleImageButton() {
        viewModelScope.launch {
            _getImageFromGallery.emit(Unit)
        }
    }

    fun proceedActivityResult(result: ActivityResult) {
        if (result.resultCode == RESULT_OK) {
            result.data?.data?.let { uri ->
                updateUiState { uiState ->
                    uiState.copy(gatheringImage = File(imageUtil.getRealPathFromURI(uri)))
                }
            }
        }
    }
}