package com.plub.presentation.ui.main.gathering.modifyGathering.recruit

import android.app.Activity
import android.net.Uri
import android.text.Editable
import androidx.activity.result.ActivityResult
import androidx.lifecycle.viewModelScope
import com.canhub.cropper.CropImageView
import com.plub.domain.UiState
import com.plub.domain.model.enums.DialogMenuItemType
import com.plub.domain.model.enums.UploadFileType
import com.plub.domain.model.vo.media.ChangeFileRequestVo
import com.plub.domain.model.vo.media.UploadFileRequestVo
import com.plub.domain.model.vo.media.UploadFileResponseVo
import com.plub.domain.model.vo.modifyGathering.ModifyRecruitRequestVo
import com.plub.domain.usecase.PostChangeFileUseCase
import com.plub.domain.usecase.PostUploadFileUseCase
import com.plub.domain.usecase.PutModifyRecruitUseCase
import com.plub.presentation.base.BaseViewModel
import com.plub.presentation.util.ImageUtil
import com.plub.presentation.util.PlubLogger
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@HiltViewModel
class ModifyRecruitViewModel @Inject constructor(
    private val imageUtil: ImageUtil,
    private val postUploadFileUseCase: PostUploadFileUseCase,
    private val postChangeFileUseCase: PostChangeFileUseCase,
    private val putModifyRecruitUseCase: PutModifyRecruitUseCase
) : BaseViewModel<ModifyRecruitPageState>(ModifyRecruitPageState()) {

    private var cameraTempImageUri: Uri? = null

    fun initPageState(bundlePageState: ModifyRecruitPageState) {
        updateUiState { uiState ->
            uiState.copy(
                plubbingId = bundlePageState.plubbingId,
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
                updatePlubbingMainImage(file)
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
        updatePlubbingMainImage(null)
    }

    private fun updatePlubbingMainImage(file: File?) {
        updateUiState { uiState ->
            uiState.copy(
                tempPlubbingMainBitmap = file
            )
        }
    }

    fun onClickSaveButton() {
        when {
            useBuiltInImageFromServer() -> { }
            useExistingImage() -> { modifyRecruit(uiState.value.plubbingMainImgUrl) }
            uploadNewImage() -> { uploadImageFileAndUpdateRecruit { url -> modifyRecruit(url) } }
            changeImage() -> { changeImageFileAndUpdateRecruit { url -> modifyRecruit(url) }}
        }
    }

    private fun useBuiltInImageFromServer(): Boolean {
        return with(uiState.value) {
            tempPlubbingMainBitmap == null && plubbingMainImgUrl.isEmpty()
        }
    }

    private fun useExistingImage(): Boolean {
        return with(uiState.value) {
            tempPlubbingMainBitmap == null && plubbingMainImgUrl.isNotEmpty()
        }
    }

    private fun uploadNewImage(): Boolean {
        return with(uiState.value) {
            tempPlubbingMainBitmap != null && plubbingMainImgUrl.isEmpty()
        }
    }

    private fun changeImage(): Boolean {
        return with(uiState.value) {
            tempPlubbingMainBitmap != null && plubbingMainImgUrl.isNotEmpty()
        }
    }

    private fun modifyRecruit(imageUrl: String) = viewModelScope.launch {
        val request = with(uiState.value) {
            ModifyRecruitRequestVo(
                plubbingId = plubbingId,
                title = title,
                name = name,
                goal = goal,
                introduce = introduce,
                mainImage = imageUrl
            )
        }

        putModifyRecruitUseCase(request).collect { state ->
            inspectUiState(state,
            succeedCallback = { },
            individualErrorCallback = null)
        }
    }

    private fun changeImageFileAndUpdateRecruit(onSuccess: (String) -> Unit) = viewModelScope.launch {
        val file = uiState.value.tempPlubbingMainBitmap
        file?.let {
            val fileRequest = ChangeFileRequestVo(
                UploadFileType.PLUBBING_MAIN,
                uiState.value.plubbingMainImgUrl,
                file
            )
            postChangeFileUseCase(fileRequest).collect { state ->
                handleOnClickSaveButton(state, onSuccess)
            }
        }
    }

    private fun uploadImageFileAndUpdateRecruit(onSuccess: (String) -> Unit) = viewModelScope.launch {
        val file = uiState.value.tempPlubbingMainBitmap
        file?.let {
            val fileRequest = UploadFileRequestVo(
                UploadFileType.PLUBBING_MAIN,
                file
            )
            postUploadFileUseCase(fileRequest).collect { state ->
                handleOnClickSaveButton(state, onSuccess)
            }
        }
    }

    private fun handleOnClickSaveButton(
        state: UiState<UploadFileResponseVo>,
        onSuccess: (String) -> Unit
    ) {
        inspectUiState(
            state,
            succeedCallback = {
                onSuccess(it.fileUrl)
            },
            individualErrorCallback = { _, _ ->
                //TODO ERROR 처리
            }
        )
    }

}