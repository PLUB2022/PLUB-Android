package com.plub.presentation.ui.main.archive.upload

import android.app.Activity
import android.net.Uri
import androidx.activity.result.ActivityResult
import androidx.lifecycle.viewModelScope
import com.plub.domain.model.enums.ArchiveItemViewType
import com.plub.domain.model.enums.UploadFileType
import com.plub.domain.model.vo.archive.ArchiveDetailResponseVo
import com.plub.domain.model.vo.archive.ArchiveUploadVo
import com.plub.domain.model.vo.archive.DetailArchiveRequestVo
import com.plub.domain.model.vo.media.UploadFileRequestVo
import com.plub.domain.model.vo.media.UploadFileResponseVo
import com.plub.domain.usecase.GetDetailArchiveUseCase
import com.plub.domain.usecase.PostUploadFileUseCase
import com.plub.presentation.base.BaseViewModel
import com.plub.presentation.ui.PageState
import com.plub.presentation.ui.main.archive.ArchiveEvent
import com.plub.presentation.ui.main.archive.bottomsheet.upload.ArchiveBottomSheetEvent
import com.plub.presentation.util.ImageUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@HiltViewModel
class ArchiveUploadViewModel @Inject constructor(
    private val postUploadFileUseCase: PostUploadFileUseCase,
    private val getDetailArchiveUseCase: GetDetailArchiveUseCase
) : BaseViewModel<ArchiveUploadPageState>(ArchiveUploadPageState()) {

    private var editText : String = ""
    private var plubbingId : Int = 0
    private var archiveId : Int = 0

    fun initPageWithFirstImage(imageUri : String){
        val firstImageList = arrayListOf<ArchiveUploadVo>(ArchiveUploadVo(viewType = ArchiveItemViewType.IMAGE_VIEW, image = imageUri))
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
        val imageCount = stateList.size - 1
        updateUiState { uiState ->
            uiState.copy(
                archiveUploadVoList = stateList,
                imageCount = imageCount
            )
        }
    }

    fun initPage(pId : Int, arcId : Int){
        plubbingId = pId
        archiveId = arcId
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
        val isDataNotEmpty = (uiState.value.imageCount > 0) && editText.isNotEmpty()
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
        val originList = uiState.value.archiveUploadVoList
        val mergeList = arrayListOf(vo)
        updateListState(originList + mergeList)
    }

    fun showBottomSheet(){
        emitEventFlow(ArchiveUploadEvent.ShowBottomSheet)
    }
}