package com.plub.presentation.ui.main.archive

import androidx.lifecycle.viewModelScope
import com.plub.domain.model.vo.archive.ArchiveCardResponseVo
import com.plub.domain.model.vo.archive.ArchiveDetailResponseVo
import com.plub.domain.model.vo.archive.BrowseAllArchiveRequestVo
import com.plub.domain.model.vo.archive.DetailArchiveRequestVo
import com.plub.domain.usecase.GetAllArchiveUseCase
import com.plub.domain.usecase.GetDetailArchiveUseCase
import com.plub.presentation.base.BaseViewModel
import com.plub.presentation.ui.PageState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ArchiveViewModel @Inject constructor(
    val getAllArchiveUseCase: GetAllArchiveUseCase,
    val getDetailArchiveUseCase: GetDetailArchiveUseCase
)  :BaseViewModel<ArchivePageState>(ArchivePageState()) {

    companion object{
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
        emitEventFlow(ArchiveEvent.SeeUploadBottomSheet)
    }
}