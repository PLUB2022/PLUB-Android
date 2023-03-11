package com.plub.presentation.ui.main.archive.bottomsheet.upload

import android.app.Activity
import android.net.Uri
import androidx.activity.result.ActivityResult
import com.canhub.cropper.CropImageView
import com.plub.presentation.base.BaseViewModel
import com.plub.presentation.ui.PageState
import com.plub.presentation.util.ImageUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ArchiveBottomSheetViewModel @Inject constructor(
    private val imageUtil: ImageUtil
) : BaseViewModel<PageState.Default>(PageState.Default) {

    private var cameraTempImageUri: Uri? = null

    fun onClickCamera(){
        cameraTempImageUri = imageUtil.getUriFromTempFileInExternalDir().also {
            emitEventFlow(ArchiveBottomSheetEvent.GoToCamera(it))
        }
    }

    fun onClickAlbum(){
        emitEventFlow(ArchiveBottomSheetEvent.GoToAlbum)
    }

    fun proceedGatheringImageFromCameraResult(result: ActivityResult) {
        if (result.resultCode == Activity.RESULT_OK) {
            cameraTempImageUri?.let { uri ->
                emitCropImageAndOptimizeEvent(uri)
            }
        }
    }

    fun proceedGatheringImageFromGalleryResult(result: ActivityResult) {
        if (result.resultCode == Activity.RESULT_OK) {
            result.data?.data?.let { uri ->
                emitCropImageAndOptimizeEvent(uri)
            }
        }
    }

    fun proceedCropImageResult(result: CropImageView.CropResult) {
        if (result.isSuccessful) {
            result.uriContent?.let { uri ->
                val file = imageUtil.uriToOptimizeImageFile(uri)
                emitEventFlow(ArchiveBottomSheetEvent.EmitImageFile(file))
            }
        }
    }

    private fun emitCropImageAndOptimizeEvent(uri: Uri) {
        emitEventFlow(ArchiveBottomSheetEvent.CropImageAndOptimize(
                imageUtil.getCropImageOptions(uri)
            )
        )
    }
}