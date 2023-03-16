package com.plub.presentation.ui.main.archive.upload

import androidx.lifecycle.viewModelScope
import com.plub.domain.model.enums.ArchiveItemViewType
import com.plub.domain.model.enums.UploadFileType
import com.plub.domain.model.vo.archive.*
import com.plub.domain.model.vo.media.DeleteFileRequestVo
import com.plub.domain.model.vo.media.UploadFileRequestVo
import com.plub.domain.model.vo.media.UploadFileResponseVo
import com.plub.domain.usecase.*
import com.plub.presentation.base.BaseViewModel
import com.plub.presentation.util.PlubLogger
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@HiltViewModel
class ArchiveUploadViewModel @Inject constructor(
    private val postUploadFileUseCase: PostUploadFileUseCase,
    private val getDetailArchiveUseCase: GetDetailArchiveUseCase,
    private val postCreateArchiveUseCase: PostCreateArchiveUseCase,
    private val deleteFileUseCase: DeleteFileUseCase,
    private val putEditArchiveUseCase: PutEditArchiveUseCase
) : BaseViewModel<ArchiveUploadPageState>(ArchiveUploadPageState()) {

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

    fun initPageWithFirstImage(imageUri : String, title : String, pId: Int){
        plubbingId = pId
        plubTitle = title
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
        updateButtonState()
        updateUiState { uiState ->
            uiState.copy(
                archiveUploadVoList = stateList,
                imageCount = imageCount,
                title = plubTitle,
                pageType = pageType,
                enableButton = isDataNotEmpty
            )
        }
    }

    private fun updateButtonState(){
        val isDataNotEmpty = ((uiState.value.imageCount > 0) && editText.isNotEmpty())
        if (isDataNotEmpty != uiState.value.enableButton){
            updateUiState { uiState ->
                uiState.copy(
                    enableButton = isDataNotEmpty
                )
            }
        }
    }

    fun initPage(pId : Int, arcId : Int, title : String){
        plubTitle = title
        plubbingId = pId
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
        for(imageContent in vo.images){
            initList.add(ArchiveUploadVo(
                viewType = ArchiveItemViewType.IMAGE_VIEW,
                image = imageContent
            ))
        }
        updateListState(mergeList + initList)
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
        val originList = uiState.value.archiveUploadVoList
        val mergeList = mutableListOf<ArchiveUploadVo>()
        mergeList.addAll(originList)
        mergeList.removeAt(position)
        updateListState(mergeList)
    }

    fun updateEditText(text : String){
        editText = text
        updateButtonState()
    }

    fun uploadImageFile(file : File?){
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
        val originList = uiState.value.archiveUploadVoList.toMutableList()
        originList.add(vo)
        val imageCount = originList.size - 2
        val last = arrayListOf(ArchiveUploadVo(viewType = ArchiveItemViewType.IMAGE_ADD_VIEW))
        if(imageCount < MAX_IMAGE) updateListState(originList + last) else updateListState(originList)
    }

    fun showBottomSheet(){
        emitEventFlow(ArchiveUploadEvent.ShowBottomSheet)
    }

    fun updateArchive(){
        when(uiState.value.pageType){
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
        val archiveList = uiState.value.archiveUploadVoList
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
                inspectUiState(state, ::handleSuccessEditArchive)
            }
        }
    }

    private fun handleSuccessEditArchive(vo : ArchiveContentResponseVo){
        emitEventFlow(ArchiveUploadEvent.GoToBack)
    }
}