package com.plub.presentation.ui.main.archive

import androidx.lifecycle.viewModelScope
import com.plub.domain.model.enums.ArchiveAccessType
import com.plub.domain.model.enums.UploadFileType
import com.plub.domain.model.vo.archive.*
import com.plub.domain.model.vo.media.UploadFileRequestVo
import com.plub.domain.model.vo.media.UploadFileResponseVo
import com.plub.domain.usecase.GetAllArchiveUseCase
import com.plub.domain.usecase.GetDetailArchiveUseCase
import com.plub.domain.usecase.PostUploadFileUseCase
import com.plub.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@HiltViewModel
class ArchiveViewModel @Inject constructor(
    val postUploadFileUseCase: PostUploadFileUseCase,
    val getAllArchiveUseCase: GetAllArchiveUseCase,
    val getDetailArchiveUseCase: GetDetailArchiveUseCase
)  :BaseViewModel<ArchivePageState>(ArchivePageState()) {

    companion object{
        const val FIRST_PAGE = Int.MAX_VALUE
    }
    private var title : String = ""
    private var cursorId : Int = FIRST_PAGE
    private var plubbingId : Int = -1
    private var hasMoreList : Boolean = false

    fun fetchArchivePage(name : String, id : Int){
        title = name
        plubbingId = id
        val request = BrowseAllArchiveRequestVo(id, cursorId)
        viewModelScope.launch {
            getAllArchiveUseCase(request).collect{ state ->
                inspectUiState(state, ::handleSuccessFetchArchives)
            }
        }
    }

    private fun handleSuccessFetchArchives(vo : ArchiveCardResponseVo){
        updateUiState { uiState ->
            uiState.copy(
                title = title,
                archiveList = vo.content,
                isLoading = hasMoreList
            )
        }
    }

    fun seeDetailDialog(id : Int){
        val request = DetailArchiveRequestVo(plubbingId, id)
        viewModelScope.launch {
            getDetailArchiveUseCase(request).collect{ state ->
                inspectUiState(state, ::handleSuccessFetchDetailArchive)
            }
        }
    }

    private fun handleSuccessFetchDetailArchive(vo : ArchiveDetailResponseVo){
        emitEventFlow(ArchiveEvent.SeeDetailArchiveDialog(vo))
    }

    fun onClickBack(){
        emitEventFlow(ArchiveEvent.GoToBack)
    }

    fun onClickUploadBottomSheet(){
        emitEventFlow(ArchiveEvent.ClickUploadBottomSheet)
    }

    fun uploadImageFile(file : File?){
        val request = file?.let { UploadFileRequestVo(UploadFileType.ARCHIVE, it) }
        viewModelScope.launch {
            if (request != null) {
                postUploadFileUseCase(request).collect{ state ->
                    inspectUiState(state, ::handleSuccessUploadImage)
                }
            }
        }
    }

    private fun handleSuccessUploadImage(vo : UploadFileResponseVo){
        emitEventFlow(ArchiveEvent.GoToArchiveUpload(vo.fileUrl, title))
    }

    fun seeBottomSheet(type : ArchiveAccessType, id : Int){
        when (type) {
            ArchiveAccessType.AUTHOR -> {
                emitEventFlow(ArchiveEvent.SeeAuthorBottomSheet(id))
            }
            ArchiveAccessType.HOST -> {
                emitEventFlow(ArchiveEvent.SeeHostBottomSheet(id))
            }
            ArchiveAccessType.NORMAL -> {
                emitEventFlow(ArchiveEvent.SeeNormalBottomSheet(id))
            }
        }
    }

    fun deleteArchive(archiveId : Int){
        val originList = mutableListOf<ArchiveContentResponseVo>()
        originList.addAll(uiState.value.archiveList)
        originList.forEach {
            if(it.archiveId == archiveId) originList.remove(it)
        }
        updateUiState { uiState ->
            uiState.copy(
                archiveList = originList
            )
        }
    }

    fun goToEdit(archiveId : Int){
        emitEventFlow(ArchiveEvent.GoToEdit(title, archiveId))
    }

    fun goToReport(archiveId: Int){
        emitEventFlow(ArchiveEvent.GoToReport(archiveId))
    }
}