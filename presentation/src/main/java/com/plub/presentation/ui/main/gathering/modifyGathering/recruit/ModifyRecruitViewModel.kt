package com.plub.presentation.ui.main.gathering.modifyGathering.recruit

import android.app.Activity
import android.graphics.Bitmap
import android.net.Uri
import android.text.Editable
import androidx.activity.result.ActivityResult
import androidx.lifecycle.viewModelScope
import com.canhub.cropper.CropImageView
import com.plub.domain.model.enums.DialogMenuItemType
import com.plub.domain.model.vo.home.recruitdetailvo.RecruitDetailResponseVo
import com.plub.domain.usecase.GetRecruitDetailUseCase
import com.plub.presentation.base.BaseViewModel
import com.plub.presentation.ui.main.gathering.createGathering.goalAndIntroduceAndImage.CreateGatheringGoalAndIntroduceAndImageEvent
import com.plub.presentation.util.ImageUtil
import com.plub.presentation.util.PlubLogger
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@HiltViewModel
class ModifyRecruitViewModel @Inject constructor(
    private val imageUtil: ImageUtil,
) : BaseViewModel<ModifyRecruitPageState>(ModifyRecruitPageState()) {

    private var cameraTempImageUri: Uri? = null

    fun initPageState(bundlePageState: ModifyRecruitPageState) {
        updateUiState { uiState ->
            uiState.copy(
                title = bundlePageState.title,
                name = bundlePageState.name,
                goal = bundlePageState.goal,
                introduce = bundlePageState.introduce,
                plubbingMainImgUrl = bundlePageState.plubbingMainImgUrl,
                tempPlubbingMainBitmap = bundlePageState.tempPlubbingMainBitmap
            )
        }
    }

    fun updateIntroductionTitle(text: Editable) {
        updateUiState { uiState ->
            uiState.copy(title = text.toString())
        }
    }

    fun updateGatheringName(text: Editable) {
        updateUiState { uiState ->
            uiState.copy(name = text.toString())
        }
    }

    fun updateGatheringGoal(text: Editable) {
        updateUiState { uiState ->
            uiState.copy(goal = text.toString())
        }
    }

    fun updateGatheringIntroduce(text: Editable) {
        updateUiState { uiState ->
            uiState.copy(introduce = text.toString())
        }
    }

    fun onClickAddSingleImageButton() {
        emitEventFlow(ModifyRecruitEvent.ShowSelectImageBottomSheetDialog)
    }

    fun proceedGatheringImageFromGalleryResult(result: ActivityResult) {
        if (result.resultCode == Activity.RESULT_OK) {
            result.data?.data?.let { uri ->
                emitCropImageAndOptimizeEvent(uri)
            }
        }
    }

    fun proceedGatheringImageFromCameraResult(result: ActivityResult) {
        if (result.resultCode == Activity.RESULT_OK) {
            cameraTempImageUri?.let { uri ->
                emitCropImageAndOptimizeEvent(uri)
            }
        }
    }

    private fun emitCropImageAndOptimizeEvent(uri: Uri) {
        emitEventFlow(
            ModifyRecruitEvent.CropImageAndOptimize(
                imageUtil.getCropImageOptions(uri)
            )
        )
    }

    fun proceedCropImageResult(result: CropImageView.CropResult) {
        if (result.isSuccessful) {
            result.uriContent?.let { uri ->
                val file = imageUtil.uriToOptimizeImageFile(uri)
                updatePlubbingMainimage(file)
            }
        }
    }

    fun onClickImageMenuItemType(type: DialogMenuItemType) {
        when (type) {
            DialogMenuItemType.CAMERA_IMAGE ->
                cameraTempImageUri = imageUtil.getUriFromTempFileInExternalDir().also {
                    emitEventFlow(ModifyRecruitEvent.GetImageFromCamera(it))
                }

            DialogMenuItemType.ALBUM_IMAGE -> emitEventFlow(
                ModifyRecruitEvent.GetImageFromGallery
            )

            else -> defaultImage()
        }
    }

    private fun defaultImage() {
        updatePlubbingMainimage(null)
    }

    private fun updatePlubbingMainimage(file: File?) {
        updateUiState { uiState ->
            uiState.copy(
                tempPlubbingMainBitmap = file
            )
        }
    }

}