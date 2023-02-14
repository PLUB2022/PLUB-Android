package com.plub.presentation.ui.main.archive

import androidx.lifecycle.viewModelScope
import com.plub.domain.model.enums.UploadFileType
import com.plub.domain.model.vo.archive.ArchiveCardResponseVo
import com.plub.domain.model.vo.archive.ArchiveDetailResponseVo
import com.plub.domain.model.vo.archive.BrowseAllArchiveRequestVo
import com.plub.domain.model.vo.archive.DetailArchiveRequestVo
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
        const val AUTHOR = "author"
        const val HOST = "host"
        const val NORMAL = "normal"
        const val FIRST_PAGE = 1
    }
    private var title : String = ""
    private var page : Int = FIRST_PAGE
    private var plubbingId : Int = -1
    private var hasMoreList : Boolean = false

    fun fetchArchivePage(name : String, id : Int){
        title = name
        plubbingId = id
        val request = BrowseAllArchiveRequestVo(id, page)
        viewModelScope.launch {
            getAllArchiveUseCase(request).collect{ state ->
                inspectUiState(state, ::handleSuccessFetchArchives)
            }
        }
    }

    private fun handleSuccessFetchArchives(vo : ArchiveCardResponseVo){
        hasMoreList = (page != vo.totalPages)
        updateUiState { uiState ->
            uiState.copy(
                title = title,
                archiveList = vo.content,
                isLoading = hasMoreList
            )
        }
        page++
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
        emitEventFlow(ArchiveEvent.GoToArchiveUpload(vo.fileUrl))
    }

    fun seeBottomSheet(type : String){
        if(type.equals(AUTHOR)){
            emitEventFlow(ArchiveEvent.SeeAuthorBottomSheet)
        }
        else if(type.equals(AUTHOR)){
            emitEventFlow(ArchiveEvent.SeeHostBottomSheet)
        }
        else if(type.equals(NORMAL)){
            emitEventFlow(ArchiveEvent.SeeNormalBottomSheet)
        }
    }
}