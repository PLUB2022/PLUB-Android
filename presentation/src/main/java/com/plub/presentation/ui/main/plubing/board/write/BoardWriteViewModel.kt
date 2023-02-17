package com.plub.presentation.ui.main.plubing.board.write

import android.net.Uri
import android.text.Editable
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import androidx.lifecycle.viewModelScope
import com.canhub.cropper.CropImageView
import com.plub.domain.model.enums.DialogMenuItemType
import com.plub.domain.model.enums.PlubingFeedType
import com.plub.domain.model.enums.UploadFileType
import com.plub.domain.model.vo.board.PostBoardRequestVo
import com.plub.domain.model.vo.board.WriteBoardFeedTypeVo
import com.plub.domain.model.vo.media.UploadFileRequestVo
import com.plub.domain.usecase.PostPlubingBoardUseCase
import com.plub.domain.usecase.PostUploadFileUseCase
import com.plub.presentation.R
import com.plub.presentation.base.BaseViewModel
import com.plub.presentation.util.ImageUtil
import com.plub.presentation.util.PermissionManager
import com.plub.presentation.util.ResourceProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject
import kotlin.properties.Delegates

@HiltViewModel
class BoardWriteViewModel @Inject constructor(
    val postUploadFileUseCase: PostUploadFileUseCase,
    val postPlubingBoardUseCase: PostPlubingBoardUseCase,
    val resourceProvider: ResourceProvider,
    val imageUtil: ImageUtil,
) : BaseViewModel<BoardWritePageState>(BoardWritePageState()) {

    companion object {
        private const val MAX_LENGTH_CONTENT = 300
    }

    private var plubingId by Delegates.notNull<Int>()
    private var cameraTempImageUri: Uri? = null

    fun initArgs(plubingId: Int, plubingName: String) {
        this.plubingId = plubingId
        updateUiState { uiState ->
            uiState.copy(
                plubingName = plubingName,
                contentMaxLength = MAX_LENGTH_CONTENT
            )
        }
    }

    fun onClickFeedType(feedType: PlubingFeedType) {
        updateUiState { uiState ->
            uiState.copy(
                feedTypeList = getFeedTypeList(feedType),
                selectedFeedType = feedType,
                isPostButtonEnable = isPostButtonEnable(_feedType = feedType)
            )
        }
    }

    fun fetchFeedTypeList() {
        updateUiState { uiState ->
            uiState.copy(
                feedTypeList = getFeedTypeList(uiState.selectedFeedType)
            )
        }
    }

    fun onClickFeedImage() {
        PermissionManager.createGetImagePermission {
            emitEventFlow(BoardWriteEvent.ShowSelectImageBottomSheetDialog)
        }
    }

    fun onContentTextChanged(text: Editable) {
        updateUiState { uiState ->
            uiState.copy(
                contentCount = getContentCountSpannableString(uiState.content.length),
                isPostButtonEnable = isPostButtonEnable(_content = text.toString())
            )
        }
    }

    fun onTitleTextChanged(text: Editable) {
        updateUiState { uiState ->
            uiState.copy(
                isPostButtonEnable = isPostButtonEnable(_title = text.toString())
            )
        }
    }

    fun onClickImageMenuItemType(type: DialogMenuItemType) {
        when (type) {
            DialogMenuItemType.CAMERA_IMAGE -> {
                cameraTempImageUri = imageUtil.getUriFromTempFileInExternalDir().also {
                    emitEventFlow(BoardWriteEvent.GoToCamera(it))
                }
            }

            DialogMenuItemType.ALBUM_IMAGE -> emitEventFlow(BoardWriteEvent.GoToAlbum)
            else -> Unit
        }
    }

    fun onTakeImageFromCamera() {
        cameraTempImageUri?.let { uri ->
            emitCropImageAndOptimizeEvent(uri)
        }
    }

    fun onSelectImageFromAlbum(uri: Uri) {
        emitCropImageAndOptimizeEvent(uri)
    }

    fun proceedCropImageResult(result: CropImageView.CropResult) {
        if (result.isSuccessful) {
            result.uriContent?.let { uri ->
                val file = imageUtil.uriToOptimizeImageFile(uri)
                updateImageFile(file)
            }
        }
    }

    fun onClickPostUpload() {
        uploadImage {
            uploadFeed(it) {
                emitEventFlow(BoardWriteEvent.CompleteWrite)
            }
        }
    }

    private fun getFeedTypeList(selectedFeedType: PlubingFeedType): List<WriteBoardFeedTypeVo> {
        return PlubingFeedType.boardWriteFeedTypeList().map {
            WriteBoardFeedTypeVo(
                it,
                it == selectedFeedType
            )
        }
    }

    private fun getContentCountSpannableString(contentLength: Int): SpannableString {
        val contentCountString = resourceProvider.getString(
            R.string.word_middle_slash,
            contentLength,
            MAX_LENGTH_CONTENT
        )
        val contentCountColor = resourceProvider.getColor(R.color.color_363636)
        return SpannableString(contentCountString).apply {
            setSpan(
                ForegroundColorSpan(contentCountColor),
                0,
                contentCountString.length,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
        }
    }

    private fun isPostButtonEnable(
        _feedType: PlubingFeedType? = null,
        _title: String? = null,
        _content: String? = null,
        _imageFile: File? = null
    ): Boolean {
        val feedType = _feedType ?: uiState.value.selectedFeedType
        val title = _title ?: uiState.value.title
        val content = _content ?: uiState.value.content
        val imageFile = _imageFile ?: uiState.value.imageFile
        return when (feedType) {
            PlubingFeedType.TEXT -> title.isNotEmpty() && content.isNotEmpty()
            PlubingFeedType.IMAGE -> title.isNotEmpty() && imageFile != null
            PlubingFeedType.TEXT_AND_IMAGE -> title.isNotEmpty() && content.isNotEmpty() && imageFile != null
        }
    }

    private fun emitCropImageAndOptimizeEvent(uri: Uri) {
        emitEventFlow(
            BoardWriteEvent.CropImageAndOptimize(
                imageUtil.getCropImageOptions(uri)
            )
        )
    }

    private fun updateImageFile(file: File?) {
        updateUiState { uiState ->
            uiState.copy(
                imageFile = file,
                isPostButtonEnable = isPostButtonEnable(_imageFile = file)
            )
        }
    }

    private fun uploadImage(onSuccess: (String) -> Unit) {
        if(uiState.value.selectedFeedType == PlubingFeedType.TEXT) onSuccess("")
        else {
            viewModelScope.launch {
                uiState.value.imageFile?.let {
                    val fileRequest = UploadFileRequestVo(UploadFileType.PLUBBING_MAIN, it)
                    postUploadFileUseCase(fileRequest).collect { state ->
                        inspectUiState(state, { vo ->
                            onSuccess(vo.fileUrl)
                        })
                    }
                } ?: onSuccess("")
            }
        }
    }

    private fun uploadFeed(imageUrl: String, onSuccess: () -> Unit) {
        viewModelScope.launch {
            val request = uiState.value.run {
                PostBoardRequestVo(plubingId, selectedFeedType, title, content, imageUrl)
            }
            postPlubingBoardUseCase(request).collect {
                inspectUiState(it, {
                    onSuccess()
                })
            }
        }
    }
}