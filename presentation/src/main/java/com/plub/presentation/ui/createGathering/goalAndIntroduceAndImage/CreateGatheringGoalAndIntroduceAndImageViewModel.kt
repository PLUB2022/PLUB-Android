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

    private val _showSelectImageBottomSheetDialog = MutableSharedFlow<Unit>(
        replay = 0,
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )
    val showSelectImageBottomSheetDialog: SharedFlow<Unit> =
        _showSelectImageBottomSheetDialog.asSharedFlow()

    private val _getImageFromGallery = MutableSharedFlow<Unit>(0, 1, BufferOverflow.DROP_OLDEST)
    val getImageFromGallery: SharedFlow<Unit> = _getImageFromGallery.asSharedFlow()

    private val _getImageFromCamera = MutableSharedFlow<Uri>(0, 1, BufferOverflow.DROP_OLDEST)
    val getImageFromCamera: SharedFlow<Uri> = _getImageFromCamera.asSharedFlow()

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
        viewModelScope.launch {
            _showSelectImageBottomSheetDialog.emit(Unit)
        }
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
            DialogMenuItemType.CAMERA_IMAGE -> viewModelScope.launch {
                cameraTempImageUri = resourceProvider.getUriFromTempFile().also {
                    _getImageFromCamera.emit(it)
                }
            }

            DialogMenuItemType.ALBUM_IMAGE -> viewModelScope.launch { _getImageFromGallery.emit(Unit) }
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