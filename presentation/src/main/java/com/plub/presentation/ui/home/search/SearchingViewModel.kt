package com.plub.presentation.ui.home.search

import androidx.lifecycle.viewModelScope
import com.plub.domain.model.enums.PlubSearchType
import com.plub.domain.model.vo.home.search.InsertRecentSearchVo
import com.plub.domain.model.vo.home.search.RecentSearchVo
import com.plub.domain.usecase.DeleteAllRecentSearchUseCase
import com.plub.domain.usecase.DeleteRecentSearchUseCase
import com.plub.domain.usecase.FetchRecentSearchUseCase
import com.plub.domain.usecase.InsertRecentSearchUseCase
import com.plub.presentation.base.BaseViewModel
import com.plub.presentation.event.SearchingEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchingViewModel @Inject constructor(
    val fetchRecentSearchUseCase: FetchRecentSearchUseCase,
    val deleteRecentSearchUseCase: DeleteRecentSearchUseCase,
    val deleteAllRecentSearchUseCase: DeleteAllRecentSearchUseCase,
    val insertRecentSearchUseCase: InsertRecentSearchUseCase,
) : BaseViewModel<SearchingPageState>(SearchingPageState()) {

    private var recentSearchCurrentSize: Int? = null

    fun onDeleteRecentSearch(id: Int) {
        viewModelScope.launch {
            deleteRecentSearchUseCase(id).collect {
                inspectUiState(it, {
                    fetchRecentSearchList()
                })
            }
        }
    }

    fun fetchRecentSearchList() {
        viewModelScope.launch {
            fetchRecentSearchUseCase(Unit).collect {
                inspectUiState(it, { list ->
                    updateSearchList(list)
                })
            }
        }
    }

    fun onDeleteAllRecentSearch() {
        viewModelScope.launch {
            deleteAllRecentSearchUseCase(Unit).collect {
                inspectUiState(it, {
                    updateSearchList(emptyList())
                })
            }
        }
    }

    fun onSearch(text: String) {
        recentSearchCurrentSize?.let {
            insertRecentSearch(it, text)
        }
    }

    fun onRecentSearch(text: String) {
        goToSearchResult(text)
    }

    private fun insertRecentSearch(currentSize: Int, text: String) {
        viewModelScope.launch {
            val searchVo = RecentSearchVo(search = text, saveTime = System.currentTimeMillis())
            val request = InsertRecentSearchVo(currentSize, searchVo)
            insertRecentSearchUseCase(request).collect {
                inspectUiState(it, {
                    goToSearchResult(text)
                })
            }
        }
    }

    private fun goToSearchResult(search: String) {
        emitEventFlow(SearchingEvent.Clear)
        emitEventFlow(SearchingEvent.HideKeyboard)
        emitEventFlow(SearchingEvent.GoToSearchResult(search))
    }

    private fun updateSearchList(list: List<RecentSearchVo>) {
        recentSearchCurrentSize = list.size
        updateUiState { uiState ->
            uiState.copy(
                recentSearchList = list,
                isEmptyViewVisible = list.isEmpty()
            )
        }
    }
}
