package com.plub.presentation.ui.main.archive.upload

import androidx.lifecycle.viewModelScope
import com.plub.domain.model.enums.ArchiveItemViewType
import com.plub.domain.model.enums.UploadFileType
import com.plub.domain.model.vo.archive.*
import com.plub.domain.model.vo.media.UploadFileRequestVo
import com.plub.domain.model.vo.media.UploadFileResponseVo
import com.plub.domain.usecase.GetDetailArchiveUseCase
import com.plub.domain.usecase.PostCreateArchiveUseCase
import com.plub.domain.usecase.PostUploadFileUseCase
import com.plub.domain.usecase.PutEditArchiveUseCase
import com.plub.presentation.base.BaseViewModel
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
    private val putEditArchiveUseCase: PutEditArchiveUseCase
) : BaseViewModel<ArchiveUploadPageState>(ArchiveUploadPageState()) {

    companion object{
        const val UPLOAD_TYPE = 0
        const val EDIT_TYPE = 1
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
        val firstImageList = arrayListOf(ArchiveUploadVo(viewType = ArchiveItemViewType.IMAGE_VIEW, image = imageUri))
        val mergeList = getEmptyMergedList()
        updateListState(mergeList + firstImageList)
    }

    private fun getEmptyMergedList() : List<ArchiveUploadVo>{
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

    fun deleteList(position : Int){
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
        val originList = mutableListOf<ArchiveUploadVo>()
        originList.addAll(uiState.value.archiveUploadVoList)
        originList.add(vo)
        updateListState(originList)
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

    private fun handleSuccessEditArchive(vo : ArchiveIdResponseVo){
        emitEventFlow(ArchiveUploadEvent.GoToBack)
    }
}