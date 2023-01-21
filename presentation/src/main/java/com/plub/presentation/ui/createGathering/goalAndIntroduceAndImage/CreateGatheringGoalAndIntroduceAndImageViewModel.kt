package com.plub.presentation.ui.createGathering.goalAndIntroduceAndImage

import android.app.Activity.RESULT_OK
import android.net.Uri
import android.text.Editable
import androidx.activity.result.ActivityResult
import androidx.lifecycle.viewModelScope
import com.plub.domain.model.enums.DialogMenuItemType
import com.plub.presentation.base.BaseViewModel
import com.plub.presentation.util.ImageUtil
import com.plub.presentation.util.ResourceProvider
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
    private val imageUtil: ImageUtil,
    val resourceProvider: ResourceProvider,
) :
    BaseViewModel<CreateGatheringGoalAndIntroduceAndPicturePageState>(
        CreateGatheringGoalAndIntroduceAndPicturePageState()
    ) {

    private var cameraTempImageUri: Uri? = null

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
        emitEventFlow(CreateGatheringGoalAndIntroduceAndImageEvent.ShowSelectImageBottomSheetDialog)
    }

    fun proceedGatheringImageFromGalleryResult(result: ActivityResult) {
        if (result.resultCode == RESULT_OK) {
            result.data?.data?.let { uri ->
                updateProfileFile(File(imageUtil.getRealPathFromURI(uri)))
            }
        }
    }

    fun proceedGatheringImageFromCameraResult(result: ActivityResult) {
        if (result.resultCode == RESULT_OK) {
            cameraTempImageUri?.path?.let {
                val file = File(it)
                updateProfileFile(file)
            }
        }
    }

    fun onClickImageMenuItemType(type: DialogMenuItemType) {
        when (type) {
            DialogMenuItemType.CAMERA_IMAGE ->
                cameraTempImageUri = resourceProvider.getUriFromTempFile().also {
                    emitEventFlow(CreateGatheringGoalAndIntroduceAndImageEvent.GetImageFromCamera(it))
                }

            DialogMenuItemType.ALBUM_IMAGE -> emitEventFlow(CreateGatheringGoalAndIntroduceAndImageEvent.GetImageFromGallery)
            else -> defaultImage()
        }
    }

    private fun defaultImage() {
        updateProfileFile(null)
    }

    private fun updateProfileFile(file: File?) {
        updateUiState { uiState ->
            uiState.copy(
                gatheringImage = file
            )
        }
    }
}