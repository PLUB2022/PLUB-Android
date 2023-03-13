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
        const val FIRST_CURSOR = Int.MAX_VALUE
    }
    private var title : String = ""
    private var cursorId : Int = FIRST_CURSOR
    private var isNetworkCall : Boolean = false
    private var plubbingId : Int = -1
    private var isLastPage : Boolean = false

    fun setTitleAndPlubbingId(name : String , id : Int){
        title = name
        plubbingId = id
    }

    fun fetchArchivePage(){
        val request = BrowseAllArchiveRequestVo(plubbingId, cursorId)
        viewModelScope.launch {
            cursorId = FIRST_CURSOR
            getAllArchiveUseCase(request).collect{ state ->
                inspectUiState(state, ::handleSuccessFetchArchives)
            }
        }
    }

    private fun handleSuccessFetchArchives(vo : ArchiveCardResponseVo){
        isNetworkCall = false
        isLastPage = vo.last

        updateUiState { uiState ->
            uiState.copy(
                title = title,
                archiveList = getMergeList(vo.content),
            )
        }
    }

    private fun getMergeList(list : List<ArchiveContentResponseVo>) : List<ArchiveContentResponseVo>{
        val originList = uiState.value.archiveList
        return if(originList.isEmpty() || cursorId == FIRST_CURSOR) list else originList + list
    }

    fun onScrollChanged(isBottom: Boolean, isDownScroll: Boolean) {
        if (isBottom && isDownScroll && !isLastPage && !isNetworkCall) onFetchBoardList()
    }

    fun onFetchBoardList() {
        isNetworkCall = true
        cursorUpdate()
        fetchArchivePage()
    }

    private fun cursorUpdate() {
        cursorId = if (uiState.value.archiveList.isEmpty()) FIRST_CURSOR
        else uiState.value.archiveList.lastOrNull()?.archiveId ?: FIRST_CURSOR
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
        emitEventFlow(ArchiveEvent.SeeDotsBottomSheet(id, type))
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