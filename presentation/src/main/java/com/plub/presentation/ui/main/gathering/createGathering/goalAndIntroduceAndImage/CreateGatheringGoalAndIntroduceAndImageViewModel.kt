package com.plub.presentation.ui.main.gathering.createGathering.goalAndIntroduceAndImage

import android.app.Activity.RESULT_OK
import android.net.Uri
import android.text.Editable
import androidx.activity.result.ActivityResult
import com.canhub.cropper.CropImageView
import com.plub.domain.model.enums.DialogMenuItemType
import com.plub.presentation.base.BaseViewModel
import com.plub.presentation.ui.PageState
import com.plub.presentation.util.ImageUtil
import com.plub.presentation.util.ResourceProvider
import dagger.hilt.android.lifecycle.HiltViewModel
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

    fun initUiState(savedUiState: PageState) {
        if (uiState.value != CreateGatheringGoalAndIntroduceAndPicturePageState())
            return

        if (savedUiState is CreateGatheringGoalAndIntroduceAndPicturePageState) {
            updateUiState { uiState ->
                uiState.copy(
                    gatheringGoal = savedUiState.gatheringGoal,
                    gatheringIntroduce = savedUiState.gatheringIntroduce,
                    gatheringImage = savedUiState.gatheringImage
                )
            }
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
                emitCropImageAndOptimizeEvent(uri)
            }
        }
    }

    fun proceedGatheringImageFromCameraResult(result: ActivityResult) {
        if (result.resultCode == RESULT_OK) {
            cameraTempImageUri?.let { uri ->
                emitCropImageAndOptimizeEvent(uri)
            }
        }
    }

    private fun emitCropImageAndOptimizeEvent(uri: Uri) {
        emitEventFlow(
            CreateGatheringGoalAndIntroduceAndImageEvent.CropImageAndOptimize(
                imageUtil.getCropImageOptions(uri)
            )
        )
    }

    fun proceedCropImageResult(result: CropImageView.CropResult) {
        if (result.isSuccessful) {
            result.uriContent?.let { uri ->
                val file = imageUtil.uriToOptimizeImageFile(uri)
                updateProfileFile(file)
            }
        }
    }

    fun onClickImageMenuItemType(type: DialogMenuItemType) {
        when (type) {
            DialogMenuItemType.CAMERA_IMAGE ->
                cameraTempImageUri = imageUtil.getUriFromTempFileInExternalDir().also {
                    emitEventFlow(CreateGatheringGoalAndIntroduceAndImageEvent.GetImageFromCamera(it))
                }

            DialogMenuItemType.ALBUM_IMAGE -> emitEventFlow(
                CreateGatheringGoalAndIntroduceAndImageEvent.GetImageFromGallery
            )

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