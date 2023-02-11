package com.plub.presentation.ui.main.archive

import androidx.lifecycle.viewModelScope
import com.plub.domain.model.vo.archive.ArchiveCardResponseVo
import com.plub.domain.model.vo.archive.BrowseAllArchiveRequestVo
import com.plub.domain.usecase.GetAllArchiveUseCase
import com.plub.presentation.base.BaseViewModel
import com.plub.presentation.ui.PageState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ArchiveViewModel @Inject constructor(
    val getAllArchiveUseCase: GetAllArchiveUseCase
)  :BaseViewModel<ArchivePageState>(ArchivePageState()) {

    companion object{
        const val FIRST_PAGE = 1
    }
    private var title : String = ""
    private var page : Int = FIRST_PAGE
    private var hasMoreList : Boolean = false

    fun fetchArchivePage(name : String, id : Int){
        title = name
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
}