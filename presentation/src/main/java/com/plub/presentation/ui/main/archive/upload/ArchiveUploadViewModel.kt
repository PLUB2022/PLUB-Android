package com.plub.presentation.ui.main.archive.upload

import android.app.Activity
import android.net.Uri
import androidx.activity.result.ActivityResult
import androidx.lifecycle.viewModelScope
import com.canhub.cropper.CropImageView
import com.plub.domain.model.enums.ArchiveItemViewType
import com.plub.domain.model.enums.DialogMenuItemType
import com.plub.domain.model.enums.UploadFileType
import com.plub.domain.model.vo.archive.*
import com.plub.domain.model.vo.media.DeleteFileRequestVo
import com.plub.domain.model.vo.media.UploadFileRequestVo
import com.plub.domain.model.vo.media.UploadFileResponseVo
import com.plub.domain.usecase.*
import com.plub.presentation.R
import com.plub.presentation.base.BaseTestViewModel
import com.plub.presentation.ui.main.archive.ArchiveEvent
import com.plub.presentation.util.ImageUtil
import com.plub.presentation.util.PlubingInfo
import com.plub.presentation.util.ResourceProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@HiltViewModel
class ArchiveUploadViewModel @Inject constructor(
    private val postUploadFileUseCase: PostUploadFileUseCase,
    private val getDetailArchiveUseCase: GetDetailArchiveUseCase,
    private val postCreateArchiveUseCase: PostCreateArchiveUseCase,
    private val deleteFileUseCase: DeleteFileUseCase,
    private val putEditArchiveUseCase: PutEditArchiveUseCase,
    private val resourceProvider: ResourceProvider,
    private val imageUtil: ImageUtil
) : BaseTestViewModel<ArchiveUploadPageState>() {

    companion object{
        const val UPLOAD_TYPE = 0
        const val EDIT_TYPE = 1

        const val MAX_IMAGE = 10

        const val DELETE_IMAGE = "DELETE SUCCESS"
    }

    private var editText : String = ""
    private var plubbingId : Int = 0
    private var archiveId : Int = 0
    private var plubTitle : String =""
    private var pageType : Int = UPLOAD_TYPE

    private var cameraTempImageUri: Uri? = null
    private val titleStateFlow : MutableStateFlow<String> = MutableStateFlow("")
    private val typeTitleStateFlow : MutableStateFlow<String> = MutableStateFlow("")
    private val enableButtonStateFlow : MutableStateFlow<Boolean> = MutableStateFlow(false)
    private val imageCountStateFlow : MutableStateFlow<Int> = MutableStateFlow(0)
    private val pageTypeStateFlow : MutableStateFlow<Int> = MutableStateFlow(0)
    private val archiveUploadVoListStateFlow : MutableStateFlow<List<ArchiveUploadVo>> = MutableStateFlow(emptyList())

    override val uiState: ArchiveUploadPageState = ArchiveUploadPageState(
        titleStateFlow.asStateFlow(),
        typeTitleStateFlow.asStateFlow(),
        enableButtonStateFlow.asStateFlow(),
        imageCountStateFlow.asStateFlow(),
        pageTypeStateFlow.asStateFlow(),
        archiveUploadVoListStateFlow.asStateFlow(),
    )

    fun initPageWithFirstImage(imageUri : String){
        plubbingId = PlubingInfo.info.plubingId
        plubTitle = PlubingInfo.info.name
        pageType = UPLOAD_TYPE
        updateListState(getFirstMergedList() + getFirstImageList(imageUri))
    }

    private fun getFirstImageList(imageUri:String) : List<ArchiveUploadVo>{
        return arrayListOf(
            ArchiveUploadVo(viewType = ArchiveItemViewType.IMAGE_VIEW, image = imageUri),
            ArchiveUploadVo(viewType = ArchiveItemViewType.IMAGE_ADD_VIEW)
        )
    }

    private fun getFirstMergedList() : List<ArchiveUploadVo>{
        val list = mutableListOf<ArchiveUploadVo>()
        list.add(ArchiveUploadVo(viewType = ArchiveItemViewType.EDIT_VIEW))
        list.add(ArchiveUploadVo(viewType = ArchiveItemViewType.IMAGE_TEXT_VIEW))
        return list
    }

    private fun updateListState(stateList : List<ArchiveUploadVo>){
        val imageCount = stateList.size - 2
        val isDataNotEmpty = (imageCount > 0 && editText.isNotEmpty())
        val subTitle = if(pageType == UPLOAD_TYPE) R.string.archive_upload_title
                        else R.string.archive_edit_title
        viewModelScope.launch {
            archiveUploadVoListStateFlow.update { stateList }
            imageCountStateFlow.update { imageCount }
            titleStateFlow.update { plubTitle }
            typeTitleStateFlow.update { resourceProvider.getString(subTitle) }
            pageTypeStateFlow.update { pageType }
            enableButtonStateFlow.update { isDataNotEmpty }
        }
    }

    private fun updateButtonState(){
        val isDataNotEmpty = ((uiState.imageCount.value > 0) && editText.isNotEmpty())
        if (isDataNotEmpty != uiState.enableButton.value){
            viewModelScope.launch{
                enableButtonStateFlow.update { isDataNotEmpty }
            }
        }
    }

    fun initPage(arcId : Int){
        plubTitle = PlubingInfo.info.name
        plubbingId = PlubingInfo.info.plubingId
        archiveId = arcId
        pageType = EDIT_TYPE
        val request = DetailArchiveRequestVo(plubbingId, archiveId)
        viewModelScope.launch {
            getDetailArchiveUseCase(request).collect{ state ->
                inspectUiState(state, ::handleSuccessGetDetailArchive)
            }
        }
    }

    private fun handleSuccessGetDetailArchive(vo : ArchiveDetailResponseVo){
        val mergeList = getMergedList(vo.title)
        val initList = mutableListOf<ArchiveUploadVo>()
        val last = arrayListOf(ArchiveUploadVo(viewType = ArchiveItemViewType.IMAGE_ADD_VIEW))
        vo.images.forEach {
            initList.add(ArchiveUploadVo(
                viewType = ArchiveItemViewType.IMAGE_VIEW,
                image = it
            ))
        }
        updateListState(mergeList + initList + last)
    }

    private fun getMergedList(title : String) : List<ArchiveUploadVo>{
        val list = mutableListOf<ArchiveUploadVo>()
        list.add(ArchiveUploadVo(viewType = ArchiveItemViewType.EDIT_VIEW, editText = title))
        list.add(ArchiveUploadVo(viewType = ArchiveItemViewType.IMAGE_TEXT_VIEW))
        return list
    }

    fun deleteList(position : Int, image : String){
        val request = DeleteFileRequestVo(UploadFileType.ARCHIVE, image)
        viewModelScope.launch {
            if(deleteFileUseCase(request) == DELETE_IMAGE){
                onDeleteSuccess(position)
            }
        }
    }

    private fun onDeleteSuccess(position : Int){
        val mergeList = uiState.archiveUploadVoList.value.toMutableList()
        mergeList.removeAt(position)
        updateListState(mergeList)
    }

    fun updateEditText(text : String){
        editText = text
        updateButtonState()
    }

    private fun uploadImageFile(file : File?){
        val request = file?.let { UploadFileRequestVo(UploadFileType.PLUBBING_MAIN, it) }
        viewModelScope.launch {
            if (request != null) {
                postUploadFileUseCase(request).collect{ state ->
                    inspectUiState(state, ::handleSuccessUploadImage)
                }
            }
        }
    }

    private fun handleSuccessUploadImage(vo : UploadFileResponseVo){
        addList(ArchiveUploadVo(
            viewType = ArchiveItemViewType.IMAGE_VIEW,
            image = vo.fileUrl
        ))
    }

    private fun addList(vo : ArchiveUploadVo){
        val originList = uiState.archiveUploadVoList.value.toMutableList()
        originList.removeLast()
        originList.add(vo)
        val imageCount = originList.size - 2
        val last = arrayListOf(ArchiveUploadVo(viewType = ArchiveItemViewType.IMAGE_ADD_VIEW))
        if(imageCount < MAX_IMAGE) updateListState(originList + last) else updateListState(originList)
    }

    fun showBottomSheet(){
        emitEventFlow(ArchiveUploadEvent.ShowBottomSheet)
    }

    fun updateArchive(){
        when(uiState.pageType.value){
            UPLOAD_TYPE ->{
                uploadArchive()
            }
            EDIT_TYPE -> {
                editArchive()
            }
        }
    }

    private fun uploadArchive(){
        val mergeList = getImageList()
        val request = CreateArchiveRequestVo(plubbingId, ArchiveContentRequestVo(editText, mergeList))
        viewModelScope.launch {
            postCreateArchiveUseCase(request).collect{ state ->
                inspectUiState(state, ::handleSuccessCreateArchive)
            }
        }
    }

    private fun getImageList() : List<String>{
        val archiveList = uiState.archiveUploadVoList.value
        val mergeImageList = mutableListOf<String>()
        for(content in archiveList){
            if(content.viewType == ArchiveItemViewType.IMAGE_VIEW){
                mergeImageList.add(content.image)
            }
        }
        return mergeImageList
    }

    private fun handleSuccessCreateArchive(vo : ArchiveIdResponseVo){
        emitEventFlow(ArchiveUploadEvent.GoToBack)
    }

    private fun editArchive(){
        val mergeList = getImageList()
        val request = EditArchiveRequestVo(plubbingId, archiveId, ArchiveContentRequestVo(editText, mergeList))
        viewModelScope.launch {
            putEditArchiveUseCase(request).collect{ state->
                inspectUiState(state, { handleSuccessEditArchive() })
            }
        }
    }

    private fun handleSuccessEditArchive(){
        emitEventFlow(ArchiveUploadEvent.GoToBack)
    }

    fun onClickImageMenuItemType(type: DialogMenuItemType) {
        when(type) {
            DialogMenuItemType.CAMERA_IMAGE -> {
                cameraTempImageUri = imageUtil.getUriFromTempFileInExternalDir().also {
                    emitEventFlow(ArchiveUploadEvent.GoToCamera(it))
                }
            }
            DialogMenuItemType.ALBUM_IMAGE -> emitEventFlow(ArchiveUploadEvent.GoToAlbum)
            else -> {}
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
            ArchiveUploadEvent.CropImageAndOptimize(
                imageUtil.getCropImageOptions(uri)
            )
        )
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
                uploadImageFile(file)
            }
        }
    }

    fun goToBack(){
        emitEventFlow(ArchiveUploadEvent.GoToBack)
    }
}