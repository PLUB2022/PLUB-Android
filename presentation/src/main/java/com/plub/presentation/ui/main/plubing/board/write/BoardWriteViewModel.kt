package com.plub.presentation.ui.main.plubing.board.write

import android.net.Uri
import android.text.Editable
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import androidx.lifecycle.viewModelScope
import com.canhub.cropper.CropImageView
import com.plub.domain.model.enums.DialogMenuItemType
import com.plub.domain.model.enums.WriteType
import com.plub.domain.model.enums.PlubingFeedType
import com.plub.domain.model.enums.UploadFileType
import com.plub.domain.model.vo.board.BoardCreateRequestVo
import com.plub.domain.model.vo.board.BoardEditRequestVo
import com.plub.domain.model.vo.board.BoardRequestVo
import com.plub.domain.model.vo.board.PlubingBoardVo
import com.plub.domain.model.vo.board.WriteBoardFeedTypeVo
import com.plub.domain.model.vo.media.UploadFileRequestVo
import com.plub.domain.usecase.GetBoardDetailUseCase
import com.plub.domain.usecase.PostBoardCreateUseCase
import com.plub.domain.usecase.PostUploadFileUseCase
import com.plub.domain.usecase.PutBoardEditUseCase
import com.plub.presentation.R
import com.plub.presentation.base.BaseTestViewModel
import com.plub.presentation.parcelableVo.ParsePlubingBoardVo
import com.plub.presentation.util.ImageUtil
import com.plub.presentation.util.PermissionManager
import com.plub.presentation.util.PlubingInfo
import com.plub.presentation.util.ResourceProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
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
) : BaseTestViewModel<BoardWritePageState>() {

    companion object {
        private const val MAX_LENGTH_CONTENT = 300
    }

    private val feedTypeListStateFlow: MutableStateFlow<List<WriteBoardFeedTypeVo>> = MutableStateFlow(emptyList())
    private val selectedFeedTypeStateFlow: MutableStateFlow<PlubingFeedType> = MutableStateFlow(PlubingFeedType.IMAGE)
    private val imageFileStateFlow: MutableStateFlow<File?> = MutableStateFlow(null)
    private val plubingNameStateFlow: MutableStateFlow<String> = MutableStateFlow(PlubingInfo.info.name)
    private val titleStateFlow: MutableStateFlow<String> = MutableStateFlow("")
    private val contentStateFlow: MutableStateFlow<String> = MutableStateFlow("")
    private val contentCountStateFlow: MutableStateFlow<SpannableString> = MutableStateFlow(SpannableString(""))
    private val contentMaxLengthStateFlow: MutableStateFlow<Int> = MutableStateFlow(MAX_LENGTH_CONTENT)
    private val isPostButtonEnableStateFlow: MutableStateFlow<Boolean> = MutableStateFlow(false)
    private val editImageUrlStateFlow: MutableStateFlow<String> = MutableStateFlow("")

    override val uiState: BoardWritePageState = BoardWritePageState(
        feedTypeListStateFlow.asStateFlow(),
        selectedFeedTypeStateFlow.asStateFlow(),
        imageFileStateFlow.asStateFlow(),
        plubingNameStateFlow.asStateFlow(),
        titleStateFlow,
        contentStateFlow,
        contentCountStateFlow.asStateFlow(),
        contentMaxLengthStateFlow.asStateFlow(),
        isPostButtonEnableStateFlow.asStateFlow(),
        editImageUrlStateFlow.asStateFlow(),
    )

    private lateinit var writeType:WriteType
    private val plubingId = PlubingInfo.info.plubingId
    private var feedId by Delegates.notNull<Int>()

    private var cameraTempImageUri: Uri? = null

    fun initArgs(args: BoardWriteFragmentArgs) {
        writeType = args.writeType
        feedId = args.feedId
    }

    fun initEditInfo() {
        if(writeType != WriteType.EDIT) return

        fetchBoardInfo {
            updateTitle(it.title)
            updateContent(it.content)
            updateEditImageUrl(it.feedImage)
            onClickFeedType(it.feedType)
        }
    }

    fun onClickFeedType(feedType: PlubingFeedType) {
        updateFeedTypeList(getFeedTypeList(feedType))
        updateSelectedFeedType(feedType)
        updateIsPostButtonEnable(isPostButtonEnable())
    }

    fun fetchFeedTypeList() {
        val feedTypeList = getFeedTypeList(selectedFeedTypeStateFlow.value)
        updateFeedTypeList(feedTypeList)
    }

    fun onClickFeedImage() {
        PermissionManager.createGetImagePermission {
            emitEventFlow(BoardWriteEvent.ShowSelectImageBottomSheetDialog)
        }
    }

    fun onContentTextChanged(text: Editable) {
        val contentCount = getContentCountSpannableString(contentStateFlow.value.length)
        updateContentCount(contentCount)
        updateIsPostButtonEnable(isPostButtonEnable())
    }

    fun onTitleTextChanged(text: Editable) {
        updateIsPostButtonEnable(isPostButtonEnable())
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
                updateEditImageUrl("")
                updateIsPostButtonEnable(isPostButtonEnable())
            }
        }
    }

    fun onClickPostUpload() {
        if(writeType == WriteType.CREATE) createFeed() else editFeed()
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

    private fun isPostButtonEnable(): Boolean {
        val feedType = selectedFeedTypeStateFlow.value
        val title = titleStateFlow.value
        val content = contentStateFlow.value
        val imageFile = imageFileStateFlow.value
        val editImageUrl = editImageUrlStateFlow.value
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
            hasEditImageUrl() -> onSuccess(editImageUrlStateFlow.value)
            else -> {
                imageFileStateFlow.value?.let {
                    postUploadImage(it, onSuccess)
                }?: onSuccess("")
            }
        }
    }

    private fun isImageFeedType():Boolean {
        return selectedFeedTypeStateFlow.value != PlubingFeedType.TEXT
    }

    private fun hasEditImageUrl():Boolean {
        val isEditType = writeType == WriteType.EDIT
        return isEditType && editImageUrlStateFlow.value.isNotEmpty()
    }

    private fun postUploadImage(imageFile: File, onSuccess: (String) -> Unit) {
        val fileRequest = UploadFileRequestVo(UploadFileType.PLUBING_BOARD, imageFile)
        viewModelScope.launch {
            postUploadFileUseCase(fileRequest).collect { state ->
                inspectUiState(state, { vo ->
                    onSuccess(vo.fileUrl)
                })
            }
        }
    }

    private fun postUploadFeed(imageUrl: String, onSuccess: () -> Unit) {
        viewModelScope.launch {
            val request = uiState.run {
                BoardCreateRequestVo(plubingId, selectedFeedType.value, title.value, content.value, imageUrl)
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
            val request = uiState.run {
                BoardEditRequestVo(plubingId, feedId, selectedFeedType.value, title.value, content.value, imageUrl)
            }
            putBoardEditUseCase(request).collect {
                inspectUiState(it,onSuccess)
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

    private fun updateFeedTypeList(list: List<WriteBoardFeedTypeVo>) {
        viewModelScope.launch {
            feedTypeListStateFlow.update { list }
        }
    }

    private fun updateSelectedFeedType(type: PlubingFeedType) {
        viewModelScope.launch {
            selectedFeedTypeStateFlow.update { type }
        }
    }

    private fun updateImageFile(file: File?) {
        viewModelScope.launch {
            imageFileStateFlow.update { file }
        }
    }

    private fun updateTitle(title: String) {
        viewModelScope.launch {
            titleStateFlow.update { title }
        }
    }

    private fun updateContent(content: String) {
        viewModelScope.launch {
            contentStateFlow.update { content }
        }
    }

    private fun updateContentCount(contentCount: SpannableString) {
        viewModelScope.launch {
            contentCountStateFlow.update { contentCount }
        }
    }

    private fun updateIsPostButtonEnable(isEnable: Boolean) {
        viewModelScope.launch {
            isPostButtonEnableStateFlow.update { isEnable }
        }
    }

    private fun updateEditImageUrl(imageUrl: String) {
        viewModelScope.launch {
            editImageUrlStateFlow.update { imageUrl }
        }
    }

    fun goToBack(){
        emitEventFlow(BoardWriteEvent.GoToBack)
    }
}