package com.plub.presentation.ui.main.plubing.board.write

import android.net.Uri
import android.text.Editable
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import androidx.lifecycle.viewModelScope
import com.canhub.cropper.CropImageView
import com.plub.domain.model.enums.DialogMenuItemType
import com.plub.domain.model.enums.PlubingBoardWriteType
import com.plub.domain.model.enums.PlubingFeedType
import com.plub.domain.model.enums.UploadFileType
import com.plub.domain.model.vo.board.BoardRequestVo
import com.plub.domain.model.vo.board.BoardEditRequestVo
import com.plub.domain.model.vo.board.PlubingBoardVo
import com.plub.domain.model.vo.board.BoardCreateRequestVo
import com.plub.domain.model.vo.board.WriteBoardFeedTypeVo
import com.plub.domain.model.vo.media.UploadFileRequestVo
import com.plub.domain.usecase.GetBoardDetailUseCase
import com.plub.domain.usecase.PostBoardCreateUseCase
import com.plub.domain.usecase.PostUploadFileUseCase
import com.plub.domain.usecase.PutBoardEditUseCase
import com.plub.presentation.R
import com.plub.presentation.base.BaseViewModel
import com.plub.presentation.parcelableVo.ParsePlubingBoardVo
import com.plub.presentation.util.ImageUtil
import com.plub.presentation.util.PermissionManager
import com.plub.presentation.util.PlubingInfo
import com.plub.presentation.util.ResourceProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject
import kotlin.properties.Delegates

@HiltViewModel
class BoardWriteViewModel @Inject constructor(
    val getBoardDetailUseCase: GetBoardDetailUseCase,
    val postUploadFileUseCase: PostUploadFileUseCase,
    val postBoardCreateUseCase: PostBoardCreateUseCase,
    val putBoardEditUseCase: PutBoardEditUseCase,
    val resourceProvider: ResourceProvider,
    val imageUtil: ImageUtil,
) : BaseViewModel<BoardWritePageState>(BoardWritePageState()) {

    companion object {
        private const val MAX_LENGTH_CONTENT = 300
    }

    private lateinit var writeType:PlubingBoardWriteType
    private val plubingId = PlubingInfo.info.plubingId
    private var feedId by Delegates.notNull<Int>()

    private var cameraTempImageUri: Uri? = null

    fun initArgs(args: BoardWriteFragmentArgs) {
        writeType = args.writeType
        feedId = args.feedId
        updateUiState { uiState ->
            uiState.copy(
                plubingName = PlubingInfo.info.name,
                contentMaxLength = MAX_LENGTH_CONTENT
            )
        }
    }

    fun initEditInfo() {
        if(writeType != PlubingBoardWriteType.EDIT) return

        fetchBoardInfo {
            updateUiState { uiState ->
                uiState.copy(
                    title = it.title,
                    content = it.content,
                    editImageUrl = it.feedImage
                )
            }
            onClickFeedType(it.feedType)
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
        if(writeType == PlubingBoardWriteType.CREATE) createFeed() else editFeed()
    }

    private fun getFeedTypeList(selectedFeedType: PlubingFeedType): List<WriteBoardFeedTypeVo> {
        return PlubingFeedType.boardWriteFeedTypeList().map {
            WriteBoardFeedTypeVo(it, it == selectedFeedType)
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
        _imageFile: File? = null,
        _editImageUrl: String? = null,
    ): Boolean {
        val feedType = _feedType ?: uiState.value.selectedFeedType
        val title = _title ?: uiState.value.title
        val content = _content ?: uiState.value.content
        val imageFile = _imageFile ?: uiState.value.imageFile
        val editImageUrl = _editImageUrl ?: uiState.value.editImageUrl
        val imageIsExist = imageFile != null || editImageUrl.isNotEmpty()
        return when (feedType) {
            PlubingFeedType.TEXT -> title.isNotEmpty() && content.isNotEmpty()
            PlubingFeedType.IMAGE -> title.isNotEmpty() && imageIsExist
            PlubingFeedType.TEXT_AND_IMAGE -> title.isNotEmpty() && content.isNotEmpty() && imageIsExist
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
                editImageUrl = "",
                isPostButtonEnable = isPostButtonEnable(_imageFile = file)
            )
        }
    }

    private fun createFeed() {
        uploadImage {
            postUploadFeed(it) {
                emitEventFlow(BoardWriteEvent.CompleteCreate)
            }
        }
    }

    private fun editFeed() {
        uploadImage {
            putEditFeed(it) { vo ->
                val parseVo = ParsePlubingBoardVo.mapToParse(vo)
                emitEventFlow(BoardWriteEvent.CompleteEdit(parseVo))
            }
        }
    }

    private fun uploadImage(onSuccess: (String) -> Unit) {
        when {
            !isImageFeedType() -> onSuccess("")
            hasEditImageUrl() -> onSuccess(uiState.value.editImageUrl)
            else -> {
                uiState.value.imageFile?.let {
                    postUploadImage(it, onSuccess)
                }?: onSuccess("")
            }
        }
    }

    private fun postUploadImage(imageFile: File, onSuccess: (String) -> Unit) {
        val fileRequest = UploadFileRequestVo(UploadFileType.PLUBBING_MAIN, imageFile)
        viewModelScope.launch {
            postUploadFileUseCase(fileRequest).collect { state ->
                inspectUiState(state, { vo ->
                    onSuccess(vo.fileUrl)
                })
            }
        }
    }

    private fun isImageFeedType():Boolean {
        return uiState.value.selectedFeedType != PlubingFeedType.TEXT
    }

    private fun hasEditImageUrl():Boolean {
        val isEditType = writeType == PlubingBoardWriteType.EDIT
        return isEditType && uiState.value.editImageUrl.isNotEmpty()
    }

    private fun postUploadFeed(imageUrl: String, onSuccess: () -> Unit) {
        viewModelScope.launch {
            val request = uiState.value.run {
                BoardCreateRequestVo(plubingId, selectedFeedType, title, content, imageUrl)
            }
            postBoardCreateUseCase(request).collect {
                inspectUiState(it, {
                    onSuccess()
                })
            }
        }
    }

    private fun putEditFeed(imageUrl: String, onSuccess: (PlubingBoardVo) -> Unit) {
        viewModelScope.launch {
            val request = uiState.value.run {
                BoardEditRequestVo(plubingId, feedId, selectedFeedType, title, content, imageUrl)
            }
            putBoardEditUseCase(request).collect {
                inspectUiState(it, { vo ->
                    onSuccess(vo)
                })
            }
        }
    }

    private fun fetchBoardInfo(onSuccess: (PlubingBoardVo) -> Unit) {
        val request = BoardRequestVo(plubingId, feedId)
        viewModelScope.launch {
            getBoardDetailUseCase(request).collect {
                inspectUiState(it, onSuccess)
            }
        }
    }
}